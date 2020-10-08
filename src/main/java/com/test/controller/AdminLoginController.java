package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.constant.RestFulCode;
import com.test.model.vo.RestFul;
import com.test.service.ILoginService;
import com.test.utils.CheckTool;
import com.test.utils.RestFulTool;
import com.test.utils.StringTool;

@Controller
@RequestMapping("/adminLogin")
public class AdminLoginController {

	@Autowired
	private ILoginService loginService;

	//
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginView() {
		return "login";
	}

	//
	@RequestMapping(value = "/verCode", method = RequestMethod.POST)
	@ResponseBody
	public RestFul<Boolean> verCode(String phone) {

		if (CheckTool.isNullOrEmpty(phone)) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.FIELD_NULL,
					RestFulCode.FIELD_NULL_STR);
		}

		if (!CheckTool.isPhone(phone)) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.PHONE_ERROR,
					RestFulCode.PHONE_ERROR_STR);
		}

		Integer code = loginService.sendVerCode(null, phone.trim());
		switch (code) {
		case 1030:
			return RestFulTool.getInstance().getRestFul(false, RestFulCode.SEND_VERCODE_ERROR,
					RestFulCode.SEND_VERCODE_ERROR_STR);
		case 1010:
			return RestFulTool.getInstance().getRestFul(false, RestFulCode.FAIL, RestFulCode.SEND_VERCODE_ERROR_STR);
		case 1000:
			return RestFulTool.getInstance().getRestFul(true, RestFulCode.SUCCESS, "验证码已发送至：" + phone);
		default:
			return RestFulTool.getInstance().getRestFul(false, RestFulCode.FAIL, "验证码发送失败");
		}

	}

	//
	@RequestMapping(value = "/loginVerCode", method = RequestMethod.POST)
	@ResponseBody
	public RestFul<String> loginVerCode(String phone, String verCode) {


		if (CheckTool.isNullOrEmpty(phone) || CheckTool.isNullOrEmpty(verCode)) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.FIELD_NULL,
					RestFulCode.FIELD_NULL_STR);
		}

		if (!CheckTool.isPhone(phone)) {
			return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.PHONE_ERROR,
					RestFulCode.PHONE_ERROR_STR);
		}

		// Token token = loginService.loginVerCodeAdmin(phone, verCode);
		// 登录判断
		return RestFulTool.getInstance().getRestFul(StringTool.empty, RestFulCode.FAIL, "登录失败，请重新登录");

	}

}