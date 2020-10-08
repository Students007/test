package com.test.dao;

import com.test.model.user.Token;

public interface ITokenDao {

	void save(Token token);

	void remove(Token token);

	Token findById(String id);

	Token findByToken(String token);
}
