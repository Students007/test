package com.test.model.user;

import com.test.model.abstraction.ModelAbstraction;
import com.test.model.enums.UserIdentity;

public class User extends ModelAbstraction {

	// 以下字段微信使用
	// private String unionid;
	// private int sex = -1;
	// private String province;
	// private String city;
	// private String country;
	// private String head;

	private String phone;
	private UserIdentity userIdentity;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserIdentity getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(UserIdentity userIdentity) {
		this.userIdentity = userIdentity;
	}

}
