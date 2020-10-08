package com.test.model.user;

import com.test.model.abstraction.ModelAbstraction;

//发送给用户的验证码
public class VerCode extends ModelAbstraction {

	private String phone;
	private String verCode;
	//后台填充时间
	private String sendDay;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}

	public String getSendDay() {
		return sendDay;
	}

	public void setSendDay(String sendDay) {
		this.sendDay = sendDay;
	}

}
