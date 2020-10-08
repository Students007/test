package com.test.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;
import com.test.constant.WechatConfig;
import com.test.model.order.Order;
import com.test.model.order.PayPre;
import com.test.model.user.User;
import com.test.model.wechat.WXPay;
import com.test.service.IOrderService;
import com.test.service.IPayWechatService;
import com.test.service.IUserService;
import com.test.utils.CheckTool;
import com.test.utils.DateTool;
import com.test.utils.HttpTool;
import com.test.utils.StringTool;

@Service("payWechatService")
public class PayWechatServiceImpl implements IPayWechatService {

	@Autowired
	IUserService userService;
	@Autowired
	IOrderService orderService;

	@Override
	public WXPay createUnifiedorder(String token, PayPre payPre) {

		WXPay payReq = new WXPay();

		User userMongo = userService.getUserByToken(token);
		if (CheckTool.isNullOrEmpty(userMongo)) {
			// 用户不存在
			payReq.setSign("用户不存在");
			payReq.setPartnerid("-1");
			return payReq;
		}

		// 金额是否需要支付
		if (payPre.getPriceAll() < 0) {
			payReq.setSign("无需支付");
			payReq.setPartnerid("0");
			return payReq;

		}

		BigDecimal nowTotalFee = new BigDecimal(payPre.getPriceAll());

		// 新建订单，生成微信预付单前
		Order order = new Order();
		order.setUserId(userMongo.getId());
		//order.setUserName(userMongo.getName());
		order.setUserPhone(userMongo.getPhone());
		order.setTaskId(payPre.getTaskId());
		order.setTaskName(payPre.getTaskName());
		order.setTotalFee(nowTotalFee);
		order.setPayStatus(1);
		order.setPayStatusStr(order.getPayStatusMap().get(1));
		orderService.save(order);

		// 拼接统一下单地址参数
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("appid", WechatConfig.appId);
		paraMap.put("mch_id", WechatConfig.mchId);

		paraMap.put("body", payPre.getTaskName());
		paraMap.put("total_fee", nowTotalFee.multiply(new BigDecimal(100)).intValue() + ""); // 支付金额，单位分

		paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
		paraMap.put("out_trade_no", order.getId());// 订单号,每次都不同 = Order.Id

		paraMap.put("spbill_create_ip", payPre.getIp());
		paraMap.put("trade_type", "APP"); // 支付类型
		paraMap.put("notify_url", WechatConfig.notify_urlWechat);//
		paraMap.put("device_info", order.getId()); // 自己的订单ID

		try {
			// 签名
			String sign = WXPayUtil.generateSignature(paraMap, WechatConfig.PATERNERKEY, SignType.MD5);
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);

			// 返回预付单信息
			String xmlStr = HttpTool.httpsRequest(WechatConfig.unifiedorder, "POST", xml);

			if (xmlStr.indexOf("SUCCESS") != -1) {
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);

				String prepayId = map.get("prepay_id");
				String noncestr = map.get("nonce_str");

				// 更新订单，预付单生成(微信支付)
				//order.setName(userMongo.getName() + paraMap.get("body"));
				order.setPrepayId(prepayId);
				order.setPayStatus(2);
				order.setPayStatusStr(order.getPayStatusMap().get(2));
				orderService.save(order);

				// 返回给前端的参数，签名使用
				String timestamp = DateTool.getSecondTimestamp() + "";

				// 生成签名
				Map<String, String> payMap = new HashMap<String, String>();
				payMap.put("appid", WechatConfig.appId);
				payMap.put("partnerid", WechatConfig.mchId);
				payMap.put("prepayid", prepayId);
				payMap.put("noncestr", noncestr);
				payMap.put("timestamp", timestamp);
				payMap.put("package", "Sign=WXPay");
				String paySign = WXPayUtil.generateSignature(payMap, WechatConfig.PATERNERKEY, SignType.MD5);

				// 返回给前端的参数，给APP
				payReq.setOrderId(order.getId());
				payReq.setAppid(WechatConfig.appId);
				payReq.setPartnerid(WechatConfig.mchId);
				payReq.setPrepayid(prepayId);
				// 暂填写固定值Sign=WXPay(微信官方指定)
				payReq.setPackageString("Sign=WXPay");
				payReq.setNoncestr(noncestr);
				payReq.setTimestamp(timestamp);
				payReq.setSign(paySign);

				return payReq;

			}

		} catch (Exception e) {
		}

		payReq.setSign("预付订单没有生成");
		return payReq;

	}

	// 微信支付的回调
	@Override
	public boolean notify(InputStream is) throws Exception {

		String xml = StringTool.inputStream2String(is);
		if (CheckTool.isNotNullOrEmpty(xml)) {
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);// 将微信发的xml转map

			if (notifyMap.get("result_code").equals("SUCCESS")) {

				String orderId = notifyMap.get("out_trade_no"); // 订单号,每次都不同 = Order.Id
				Order order = orderService.getById(orderId);

				String amountpaid = notifyMap.get("total_fee");// 实际支付的订单金额:单位 分
				BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);// 将分转换成元-实际支付金额:元

				// 更新新订单，更新订单信息，amountPay
				order.setAmountPay(amountPay);
				order.setPayStatus(3);
				order.setPayStatusStr(order.getPayStatusMap().get(3));
				orderService.save(order);

				// 更新任务状态

				return true;

			}

		}

		return false;

	}

}
