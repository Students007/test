package com.test.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.dao.IVerCodeDao;
import com.test.model.user.VerCode;

@Repository
public class VerCodeDaoImpl implements IVerCodeDao {

	@Override
	public void save(VerCode verCode) {
	}

	@Override
	public List<VerCode> findTodays(String phone) {
		return null;
	}

	@Override
	public List<VerCode> findByPhoneVerCode(String phone, VerCode verCode) {
		return null;
	}

	@Override
	public void remove(VerCode verCode) {
	}

}