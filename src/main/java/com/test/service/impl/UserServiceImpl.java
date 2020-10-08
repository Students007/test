package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.IUserDao;
import com.test.model.enums.UserIdentity;
import com.test.model.user.Token;
import com.test.model.user.User;
import com.test.model.wechat.WXUserInfo;
import com.test.service.ITokenService;
import com.test.service.IUserService;
import com.test.utils.CheckTool;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private ITokenService tokenService;

	@Override
	public User getUserByPhone(String phone) {
		return userDao.findByPhone(phone);
	}

	@Override
	public Token registorUpateUser(Token token, String phone, WXUserInfo wxUserInfo) {

		User user = getById(token.getId());
		if (CheckTool.isNullOrEmpty(user)) {

			// 新建一个用户
			user = new User();
			// user.setName("匿名用户（请修改姓名）");
			user.setId(token.getId());

		}

		// 手机号码
		if (CheckTool.isNullOrEmpty(phone)) {
			user.setPhone("暂无手机号码");
		} else {
			user.setPhone(phone);
		}

		user.setState(true);
		user.setUserIdentity(UserIdentity.USER);
		userDao.save(user);

		return tokenService.saveOrUpdateToken(user);

	}

	@Override
	public User getUserByToken(String token) {

		Token tokenMongo = tokenService.getByToken(token);
		if (CheckTool.isNullOrEmpty(tokenMongo)) {
			return null;
		}

		User user = userDao.findById(tokenMongo.getId());
		if (CheckTool.isNullOrEmpty(user)) {
			return null;
		}

		return user;
	}

	@Override
	public Token mergeUser(Token tokenNew, User userOldMaster) {

		if (!tokenNew.getId().equals(userOldMaster.getId())) {

			// 删除新注册的临时用户
			User userNew = getById(tokenNew.getId());
			if (CheckTool.isNotNullOrEmpty(userNew)) {
				userDao.remove(userNew);
			}

			// 删除老Token
			Token tokenMongoOld = tokenService.getById(userOldMaster.getId());
			if (CheckTool.isNotNullOrEmpty(tokenMongoOld)) {
				tokenService.remove(tokenMongoOld);
			}

			// 新Token和老用户ID不一致问题
			tokenService.remove(tokenNew);
			tokenNew.setId(userOldMaster.getId());
			// tokenNew.setName(userOldMaster.getName());
			tokenService.save(tokenNew);

			return tokenNew;

		}

		return tokenNew;

	}

	// 通过ID获取用户
	@Override
	public User getById(String id) {
		return userDao.findById(id);
	}

	// 更新用户信息
	@Override
	public boolean update(String token, User user) {

		Token tokenMongo = tokenService.getByToken(token);
		if (CheckTool.isNullOrEmpty(tokenMongo)) {
			return false;
		}

		User userMongo = userDao.findById(tokenMongo.getId());
		if (CheckTool.isNullOrEmpty(user)) {
			return false;
		}

		// 姓名
		// if (CheckTool.isNotNullOrEmpty(user.getName())) {
		// userMongo.setName(user.getName());
		// tokenMongo.setName(user.getName());
		// }

		tokenService.save(tokenMongo);
		userDao.save(userMongo);

		return true;
	}

}
