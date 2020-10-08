package com.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.constant.RestFulCode;
import com.test.dao.IVerCodeDao;
import com.test.model.user.Token;
import com.test.model.user.User;
import com.test.model.user.VerCode;
import com.test.service.IAliyunService;
import com.test.service.ILoginService;
import com.test.service.ITokenService;
import com.test.service.IUserService;
import com.test.utils.CheckTool;
import com.test.utils.DateTool;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private IVerCodeDao verCodeDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private ITokenService tokenService;
	@Autowired
	private IAliyunService aliyunService;

	@Override
	public int sendVerCode(String token, String phone) {

		// 检查今天发送次数
		List<VerCode> verCodes = verCodeDao.findTodays(phone);

		// 一天只能发送20次
		if (verCodes.size() > 19) {
			return RestFulCode.SEND_VERCODE_ERROR;
		}

		String code = aliyunService.send(phone);
		if ("-1".equals(code)) {
			return RestFulCode.SEND_VERCODE_ERROR;
		}

		if (CheckTool.isNullOrEmpty(code)) {
			return RestFulCode.FAIL;
		}

		VerCode verCode = new VerCode();
		//verCode.setName("向" + phone + "发送验证码");
		verCode.setState(true);
		verCode.setPhone(phone);
		verCode.setVerCode(code);
		verCode.setSendDay(DateTool.getToday());
		verCodeDao.save(verCode);

		return RestFulCode.SUCCESS;

	}

	@Override
	public Token loginVerCode(String token, String phone, VerCode verCode) {

		// 验证用户使用Token
		Token tokenMongoNew = tokenService.getByToken(token);
		if (CheckTool.isNullOrEmpty(tokenMongoNew)) {

			Token tokenNew = new Token();
			//tokenNew.setName("用户使用Token在服务器不存在");
			tokenNew.setToken("-1");
			return tokenNew;
		}

		// 验证用户使用验证码
		List<VerCode> verCodes = verCodeDao.findByPhoneVerCode(phone, verCode);
		if (CheckTool.isNullOrEmpty(verCodes)) {

			Token tokenNew = new Token();
			//tokenNew.setName("用户使用验证码在服务器不存在");
			tokenNew.setToken("-1");
			return tokenNew;

		} else {

			// 验证码设置失效
			for (VerCode v : verCodes) {
				v.setState(false);
				verCodeDao.save(v);
			}
		}

		// 卸载后再次安装，老用户问题
		User userOld = userService.getUserByPhone(phone);
		if (CheckTool.isNotNullOrEmpty(userOld)) {

			// 此用户存在，且已经绑定手机号码

			// 合并用户信息
			return userService.mergeUser(tokenMongoNew, userOld);

		}

		// 先绑定一下
		tokenMongoNew = userService.registorUpateUser(tokenMongoNew, phone, null);

		User userReg = userService.getUserByPhone(phone);

		// 是否注册成功
		if (CheckTool.isNullOrEmpty(userReg)) {
			return null;

		} else {
			return tokenMongoNew;
		}

	}

}