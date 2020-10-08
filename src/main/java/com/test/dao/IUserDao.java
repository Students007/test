package com.test.dao;

import com.test.model.user.User;

public interface IUserDao {

	void save(User user);

	User findByPhone(String phone);

	User findById(String id);

	void remove(User user);

}
