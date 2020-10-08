package com.test.dao;

import java.util.List;

import com.test.model.user.VerCode;

public interface IVerCodeDao {

	void save(VerCode verCode);

	void remove(VerCode verCode);

	List<VerCode> findTodays(String phone);

	List<VerCode> findByPhoneVerCode(String phone, VerCode verCode);

}
