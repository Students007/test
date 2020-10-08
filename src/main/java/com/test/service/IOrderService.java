package com.test.service;

import com.test.model.order.Order;

public interface IOrderService {

	void save(Order order);

	Order getById(String id);

	int getState(String token, String orderId);
}
