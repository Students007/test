package com.test.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.test.dao.ITokenDao;
import com.test.model.user.Token;
import com.test.utils.CheckTool;
import com.test.utils.HttpTool;

public class RestFulInterceptor implements HandlerInterceptor {

	@Autowired
	private ITokenDao tokenDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		String uri = request.getRequestURI();

		if (uri.endsWith("token/get")) {
			// 获取Token
			return true;
		} else if (uri.contains("admin")) {
			// 登录
			return true;
		} else {

			// 直接验证Token是否有效
			String token = HttpTool.getToken(request);
			if (CheckTool.isNullOrEmpty(token)) {
				return false;
			}

			Token tokenMongo = tokenDao.findByToken(token);
			if (CheckTool.isNullOrEmpty(tokenMongo)) {
				return false;
			}

			if (tokenMongo.getState()) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

}