package com.test.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.ITokenDao;
import com.test.model.user.Token;
import com.test.model.user.User;
import com.test.service.ITokenService;
import com.test.service.IUserService;
import com.test.utils.CheckTool;

@Service("tokenService")
public class TokenServiceImpl implements ITokenService {

	@Autowired
	private ITokenDao tokenDao;
	@Autowired
	private IUserService userService;

	@Override
	public Token saveOrUpdateToken(User user) {

		Token tokenMongo = tokenDao.findById(user.getId());
		if (CheckTool.isNullOrEmpty(tokenMongo)) {
			//不存在
			tokenMongo = new Token();
			tokenMongo.setId(user.getId());
		}

		//tokenMongo.setName(user.getName());
		tokenMongo.setToken(getToken());
		tokenMongo.setState(true);
		tokenDao.save(tokenMongo);

		return tokenMongo;

	}

	@Override
	public String getToken() {
		try {
			return UUID.randomUUID().toString().replaceAll("-", "") + String.valueOf(System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return UUID.randomUUID().toString().replaceAll("-", "") + String.valueOf(System.currentTimeMillis());
	}

	@Override
	public Token getById(String id) {
		return tokenDao.findById(id);
	}

	@Override
	public Token getByToken(String token) {

		if (CheckTool.isNullOrEmpty(token)) {
			return null;
		}

		return tokenDao.findByToken(token);
	}

	@Override
	public String get(String token) {

		User userMongo = userService.getUserByToken(token);

		if (CheckTool.isNotNullOrEmpty(userMongo)) {
			//如果用户存在，则更新Token
			Token tokenMongo= saveOrUpdateToken(userMongo);
			return tokenMongo.getToken();
		}

		Token tokenMongo = new Token();
		//tokenMongo.setName("用户未登录前获取的Token");
		tokenMongo.setState(true);
		tokenMongo.setToken(getToken());
		tokenDao.save(tokenMongo);

		// 创建默认用户
		tokenMongo = userService.registorUpateUser(tokenMongo, null, null);

		return tokenMongo.getToken();
	}

	@Override
	public void save(Token token) {
		tokenDao.save(token);
	}

	@Override
	public void remove(Token token) {
		tokenDao.remove(token);
	}

}
