package com.test.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.constant.RestFulCode;
import com.test.model.vo.RestFul;
import com.test.service.IOrderService;
import com.test.service.IPayWechatService;
import com.test.utils.HttpTool;
import com.test.utils.RestFulTool;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IPayWechatService payWechatService;
	@Autowired
	private IOrderService orderService;

	/**
	 * @Title: callBack
	 * @Description: 支付完成的回调函数
	 * @param:
	 * @return:
	 */
	@RequestMapping(value = "/notifyWechat", method = RequestMethod.POST)
	public String callBackWechat(HttpServletRequest request, HttpServletResponse response) {

		InputStream is = null;
		try {

			is = request.getInputStream();// 获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)

			boolean tag = payWechatService.notify(is);
			if (tag) {
				// 告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
				response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/getState", method = RequestMethod.POST)
	public RestFul<String> getState(HttpServletRequest request, String orderId) {

		String token = HttpTool.getToken(request);

		int payStatus = orderService.getState(token, orderId);

		return RestFulTool.getInstance().getRestFul(payStatus, RestFulCode.SUCCESS, RestFulCode.SUCCESS_STR);
	}

}