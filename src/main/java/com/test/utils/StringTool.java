package com.test.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.util.DigestUtils;

import com.google.gson.Gson;

public class StringTool {

	static Gson gson = new Gson();
	// 盐，用于混交md5
	private static final String slat = "&%5123***&&%%$$#@";

	public static String empty = "";

	public static String getId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String beanToJSONString(Object bean) {
		return gson.toJson(bean);
	}

	public static String getRandomString(int length) {
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	public static String getRandomInt4() {
		double ori = Math.random() * 9000 + 1000;
		return (int) Math.ceil(ori) + "";
	}

	public static boolean isEnglish(String charaString) {
		return charaString.matches("^[a-zA-Z]*");
	}

	public static boolean isSpecialChar(String str) {
		String specialChar = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";

		if (specialChar.contains(str)) {
			return true;
		}

		return false;

	}

	public static String getRandomInt(int length) {
		String str = "0123456789";
		String uuid = new String();
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			uuid += ch;
		}
		return uuid;
	}

	public static Map<String, String> json2Map(String answers) {
		Map<String, String> map = new HashMap<String, String>();
		map = gson.fromJson(answers, map.getClass());
		return map;
	}

	public static Object jsonToObject(String json, Class<?> beanClass) {
		Object res = gson.fromJson(json, beanClass);
		return res;
	}

	public static String getMD5(String str) {
		String base = str + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	public static String inputStream2String(InputStream input) {

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];

		try {
			for (int n; (n = input.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
		} catch (Exception e) {
		}

		return out.toString();

	}

}
