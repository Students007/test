package com.test.model.user;

import com.test.model.abstraction.ModelAbstraction;

//用户Token
public class Token extends ModelAbstraction {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
