package com.demo.order.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.order.service.entity.Order;
import com.demo.order.service.entity.OrderEvent;

public interface OrderService {

	Page<Order> findAll(Pageable pageable);

	Order findById(Integer id);

	void deleteAll();

	void deleteById(Integer id);

	void save(Order order);

	void createOrder(Order order);

	boolean processOrderEvent(OrderEvent orderEvent);
}
