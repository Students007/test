package com.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.constant.RestFulCode;
import com.test.model.user.Token;
import com.test.model.user.VerCode;
import com.test.model.vo.RestFul;
import com.test.service.ILoginService;
import com.test.utils.CheckTool;
import com.test.utils.HttpTool;
import com.test.utils.RestFulTool;
import com.test.utils.StringTool;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ILoginService loginService;

	@ResponseBody
	@RequestMapping(value = "/verCode", method = RequestMethod.POST)
	public RestFul<String> verCode(HttpServletRequest request, VerCode verCode) {

		String token = HttpTool.getToken(request);

		if (CheckTool.isNullOrEmpty(token)) {
			return null;
		}


		if (CheckTool.isNullOrEmpty(verCode.getPhone()) || !CheckTool.isPhone(verCode.getPhone())) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.PHONE_ERROR,
					RestFulCode.PHONE_ERROR_STR);
		}

		Integer code = loginService.sendVerCode(token, verCode.getPhone().trim());

		switch (code) {
		case 1030:
			return RestFulTool.getInstance().getRestFul(false, RestFulCode.SEND_VERCODE_ERROR,
					RestFulCode.SEND_VERCODE_ERROR_STR);
		case 1010:
			return RestFulTool.getInstance().getRestFul(false, RestFulCode.FAIL, RestFulCode.SEND_VERCODE_ERROR_STR);
		case 1000:
			//发送成功
			return RestFulTool.getInstance().getRestFul(true, RestFulCode.SUCCESS, "验证码已发送至：" + verCode.getPhone());
		default:
			return RestFulTool.getInstance().getRestFul(false, RestFulCode.FAIL, "验证码发送失败");
		}
	}


	//验证码登录
	@ResponseBody
	@RequestMapping(value = "/loginVerCode", method = RequestMethod.POST)
	public RestFul<String> loginVerCode(HttpServletRequest request, VerCode verCode) {

		String token = HttpTool.getToken(request);
		String phone = verCode.getPhone();
		verCode.getVerCode();

		if (CheckTool.isNullOrEmpty(phone) || CheckTool.isNullOrEmpty(verCode)) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.FIELD_NULL,
					RestFulCode.FIELD_NULL_STR);
		}

		if (!CheckTool.isPhone(phone)) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.PHONE_ERROR,
					RestFulCode.PHONE_ERROR_STR);
		}

		Token tokenMongo = loginService.loginVerCode(token, phone, verCode);

		// 登录判断
		if (CheckTool.isNullOrEmpty(tokenMongo)) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.FAIL, "登录失败，请重新登录");

		} else {
			token = tokenMongo.getToken();
		}

		if ("-1".equals(tokenMongo.getToken())) {
			return RestFulTool.getInstance().getRestFul("-1", RestFulCode.VERCODE_NO, RestFulCode.VERCODE_NO_STR);

		} else {
			return RestFulTool.getInstance().getRestFul(tokenMongo.getToken(), RestFulCode.SUCCESS,
					RestFulCode.SUCCESS_STR);
		}

	}



}