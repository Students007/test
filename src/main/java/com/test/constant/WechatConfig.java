package com.test.constant;

public class WechatConfig {

	// APP基本信息
	public static String appId = "wx0cf3a739107e8bea";
	public static String secret = "c6fa850af9e9e093f0b57d1e1268de81";
	// 微信支付常量
	public static String mchId = "1569644541";
	public static final String PATERNERKEY = "1617f4bab5f24055a077ac4aa474cfA7";
	// 统一下单
	public static String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 支付回调
	public static String notify_urlWechat = DefaultConfig.domain + "order/notifyWechat";

	// 微信登录
	public static String access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
	public static String userinfo_url = "https://api.weixin.qq.com/sns/userinfo?";

}
