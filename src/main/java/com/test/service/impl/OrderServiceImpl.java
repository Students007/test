package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.IOrderDao;
import com.test.model.order.Order;
import com.test.model.user.User;
import com.test.service.IOrderService;
import com.test.service.IUserService;
import com.test.utils.CheckTool;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	@Autowired
	IOrderDao orderDao;
	@Autowired
	IUserService userService;

	@Override
	public void save(Order order) {
		orderDao.save(order);
	}

	@Override
	public Order getById(String id) {
		return orderDao.findById(id);
	}

	// 查询订单状态:
	// 1:新建订单 ,2:预付单生成(微信支付), 3:支付成功(微信支付), -1:暂无用户, -2:VIP失效, -3:暂无此订单
	@Override
	public int getState(String token, String orderId) {

		User userMongo = userService.getUserByToken(token);
		if (CheckTool.isNullOrEmpty(userMongo)) {
			return -1;
		}

		Order order = orderDao.findById(orderId);
		if (CheckTool.isNullOrEmpty(order)) {
			return -1;
		}

		return order.getPayStatus();
	}

}
