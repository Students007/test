package com.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.test.dao.ITokenDao;
import com.test.model.user.Token;

@Repository
public class TokenDaoImpl implements ITokenDao {

	@Override
	public void save(Token token) {
	}

	@Override
	public Token findById(String id) {
		return null;
	}

	@Override
	public Token findByToken(String token) {
		return null;
	}

	@Override
	public void remove(Token token) {
	}
}
