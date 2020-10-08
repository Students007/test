package com.test.service;

import java.io.InputStream;

import com.test.model.order.PayPre;
import com.test.model.wechat.WXPay;

public interface IPayWechatService {

	WXPay createUnifiedorder(String token, PayPre payPre);

	boolean notify(InputStream is) throws Exception;

}
