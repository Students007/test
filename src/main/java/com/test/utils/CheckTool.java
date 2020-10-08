package com.test.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckTool {

	public static boolean isNumber(Object o) {
		return (Pattern.compile("[0-9]*")).matcher(String.valueOf(o)).matches();
	}

	//
	public static boolean isNotNullOrEmpty(Object o) {
		return !isNullOrEmpty(o);
	}

	//
	public static boolean isNullOrEmpty(Object obj) {

		if (obj == "undefind" || "undefind".equals(obj)) {
			return true;
		}

		if (obj == null) {
			return true;
		}

		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}

		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (Object element : object) {
				if (!isNullOrEmpty(element)) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	//
	public static boolean isPhone(String phone) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			if (!isMatch) {
				return false;
			}
			return isMatch;
		}
	}

	public static boolean isWin() {

		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return true;
		}
		return false;

	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

}
