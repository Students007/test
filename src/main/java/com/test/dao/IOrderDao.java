package com.test.dao;

import com.test.model.order.Order;

public interface IOrderDao {

	void save(Order order);

	Order findById(String id);

}
