package com.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.constant.RestFulCode;
import com.test.model.vo.RestFul;
import com.test.model.wallet.Wallet;
import com.test.service.IWalletService;
import com.test.utils.HttpTool;
import com.test.utils.RestFulTool;
import com.test.utils.StringTool;

@Controller
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private IWalletService walletService;

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public RestFul<String> checkList(HttpServletRequest request) {

		String token = HttpTool.getToken(request);

		Wallet wallet = walletService.get(token);

		return RestFulTool.getInstance().getRestFul(StringTool.beanToJSONString(wallet), RestFulCode.SUCCESS,
				RestFulCode.SUCCESS_STR);

	}

}