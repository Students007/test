package com.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.test.dao.IOrderDao;
import com.test.model.order.Order;

@Repository
public class OrderDaoImpl implements IOrderDao {


	@Override
	public void save(Order order) {
	}

	@Override
	public Order findById(String id) {
		return null;
	}

}