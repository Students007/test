package com.test.service;

import com.test.model.user.Token;
import com.test.model.user.VerCode;

public interface ILoginService {

	int sendVerCode(String token, String phone);

	Token loginVerCode(String token, String phone, VerCode verCode);

}
