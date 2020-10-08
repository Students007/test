package com.test.constant;

public class RestFulCode {

	public static int SUCCESS = 1000;
	public static String SUCCESS_STR = "成功";

	public static int FAIL = 1010;
	public static String FAIL_STR = "失败";

	public static int ILLEGAL = 1020;
	public static String ILLEGAL_STR = "非法客户端";

	public static int PHONE_ERROR = 1030;
	public static String PHONE_ERROR_STR = "手机号码格式不正确";

	public static int SEND_VERCODE_ERROR = 1040;
	public static String SEND_VERCODE_ERROR_STR = "验证码发今日发送次数过多";

	public static int REGISTER_RE = 1050;
	public static String REGISTER_RE_STR = "此手机号码已经注册";

	public static int VERCODE_NO = 1060;
	public static String VERCODE_NO_STR = "验证码不符";

	public static int REGISTER_OK = 1070;
	public static String REGISTER_OK_STR = "手机号注册成功";

	public static int NULL = 1080;
	public static String NULL_STR = "此请求下无数据";

	public static int FIELD_NULL = 1090;
	public static String FIELD_NULL_STR = "请求必要字段不全";

	public static int REPEAT = 1100;
	public static String REPEAT_STR = "重复";

	public static int NO_LOGIC = 1110;
	public static String NO_LOGIC_STR = "无法删除，有关联关系存在";

	public static int NO_EDIT = 1120;
	public static String NO_EDIT_STR = "无法再次编辑";

	public static int NO_USER = 1130;
	public static String NO_USER_STR = "无此用户";

	public static int NO_IDENTITY = 1140;
	public static String NO_IDENTITY_STR = "无权限";

}
