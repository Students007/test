package com.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.model.enums.UserIdentity;
import com.test.model.user.User;
import com.test.service.IUserService;
import com.test.utils.CheckTool;

@Controller
@RequestMapping("/adminIndex")
public class AdminIndexController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String loginView(String token) {

		User userMongo = userService.getUserByToken(token);
		if (CheckTool.isNullOrEmpty(userMongo)) {
			return "login";
		}

		if (userMongo.getUserIdentity() == UserIdentity.ADMIN) {
			return "index";
		} else {
			return "login";
		}

	}

}