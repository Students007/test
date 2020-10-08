package com.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.test.dao.IUserDao;
import com.test.model.user.User;

@Repository
public class UserDaoImpl implements IUserDao {

	@Override
	public void save(User user) {
	}

	@Override
	public User findByPhone(String phone) {
		return null;
	}

	@Override
	public User findById(String id) {
		return null;
	}

	@Override
	public void remove(User user) {
	}

}