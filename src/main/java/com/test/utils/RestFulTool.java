package com.test.utils;

import com.test.model.vo.RestFul;

public class RestFulTool<T> {

	private static RestFulTool instance;

	public static RestFulTool getInstance() {

		if (instance == null) {
			instance = new RestFulTool();
		}

		return instance;
	}

	public RestFul<T> getRestFul(T o, int status, String msg) {
		RestFul<T> restFul = new RestFul<T>();
		restFul.setStatus(status);
		restFul.setMsg(msg);
		restFul.setData(o);
		return restFul;
	}

}
