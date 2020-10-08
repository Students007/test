package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.IWalletDao;
import com.test.model.user.User;
import com.test.model.wallet.Wallet;
import com.test.service.IUserService;
import com.test.service.IWalletService;
import com.test.utils.CheckTool;

@Service("walletService")
public class WalletServiceImpl implements IWalletService {

	@Autowired
	IWalletDao walletDao;
	@Autowired
	IUserService userService;

	@Override
	public Wallet get(String token) {

		// 查询用户
		User userMongo = userService.getUserByToken(token);
		if (CheckTool.isNullOrEmpty(userMongo)) {
			return null;
		}

		Wallet wallet = new Wallet();
		wallet.setUserId(userMongo.getId());

		walletDao.save(wallet);

		return wallet;

	}

}
