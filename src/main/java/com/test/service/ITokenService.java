package com.test.service;

import com.test.model.user.Token;
import com.test.model.user.User;

public interface ITokenService {

	Token getById(String id);

	Token saveOrUpdateToken(User user);

	String getToken();

	Token getByToken(String token);

	String get(String token);

	void save(Token token);

	void remove(Token token);

}
