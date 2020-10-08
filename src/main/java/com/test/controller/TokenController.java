package com.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.service.ITokenService;
import com.test.utils.HttpTool;

@Controller
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private ITokenService tokenService;

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public String get(HttpServletRequest request) {

		String token = HttpTool.getToken(request);
		return tokenService.get(token);

	}

}