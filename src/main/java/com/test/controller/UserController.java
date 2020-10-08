package com.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.constant.RestFulCode;
import com.test.model.user.User;
import com.test.model.vo.RestFul;
import com.test.service.IUserService;
import com.test.utils.CheckTool;
import com.test.utils.HttpTool;
import com.test.utils.RestFulTool;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public RestFul<String> checkList(HttpServletRequest request) {

		String token = HttpTool.getToken(request);

		User user = userService.getUserByToken(token);

		if (CheckTool.isNullOrEmpty(user)) {
			return RestFulTool.getInstance().getRestFul("匿名", RestFulCode.NULL, RestFulCode.NULL_STR);
		}
		return null;

		//return RestFulTool.getInstance().getRestFul(user.getName(), RestFulCode.SUCCESS, RestFulCode.SUCCESS_STR);

	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestFul<String> update(HttpServletRequest request, User user) {

		String token = HttpTool.getToken(request);

		//		//用户名解码
		//		if (CheckTool.isNotNullOrEmpty(user.getName())) {
		//			try {
		//				//user.setName(URLDecoder.decode(user.getName(), "UTF-8"));
		//			} catch (UnsupportedEncodingException e) {
		//				e.printStackTrace();
		//			}
		//		}

		boolean tag = userService.update(token, user);

		return RestFulTool.getInstance().getRestFul(tag, RestFulCode.SUCCESS, RestFulCode.SUCCESS_STR);

	}

}