package com.test.service;

import com.test.model.user.Token;
import com.test.model.user.User;
import com.test.model.wechat.WXUserInfo;

public interface IUserService {

	User getById(String id);

	User getUserByPhone(String phone);

	Token registorUpateUser(Token token, String phone, WXUserInfo wxUserInfo);

	User getUserByToken(String token);

	Token mergeUser(Token tokenMongoNew, User userOldMaster);

	boolean update(String token, User user);

}
