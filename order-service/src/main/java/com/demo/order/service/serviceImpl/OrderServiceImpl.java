package com.demo.order.service.serviceImpl;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.order.service.entity.Order;
import com.demo.order.service.entity.OrderEvent;
import com.demo.order.service.enums.OrderEventType;
import com.demo.order.service.feignClient.PaymentServiceClient;
import com.demo.order.service.model.Invoice;
import com.demo.order.service.repository.OrderRepository;
import com.demo.order.service.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;

	@Autowired
	PaymentServiceClient paymentServiceClient;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public Order findById(Integer id) {
		return orderRepository.findById(id).get();
	}

	@Override
	public void deleteAll() {
		orderRepository.deleteAll();
	}

	@Override
	public void deleteById(Integer id) {
		orderRepository.deleteById(id);
	}

	@Override
	public void save(Order order) {
		if (order.getOrderItems().size() > 0) {
			order.getOrderItems().stream().forEach(orderItem -> {
				orderItem.setOrder(order);
			});
		}
		this.orderRepository.save(order);
	}

	@Override
	public void createOrder(Order order) {
		order.setOrderId(RandomUtils.nextInt());
		System.out.println(
				"creating invoice for order " + order.getOrderId() + " of total price " + order.getTotalPrice());
		Invoice invoice = new Invoice(order.getOrderId(), order.getTotalPrice());
		paymentServiceClient.createPayment(invoice);
		this.orderRepository.save(order);
	}

	// This is reserved for future enhancement
	public boolean processOrderEvent(OrderEvent orderEvent) {
		System.out.println(
				"Processing order event " + orderEvent.getOrderId() + " with status " + orderEvent.getEventType());
		Order order = this.orderRepository.findById(orderEvent.getOrderId()).get();
		if (order == null || order.getOredrStatus() != Order.OrderStatus.PENDING) {
			System.out.println("order not found or already processed, discarding request");
			return false;
		}
		OrderEventType result = orderEvent.getEventType();
		if (result == OrderEventType.SUCCEED) {
			System.out.println("Payment succeeded");
			order.setOredrStatus(Order.OrderStatus.APPROVED);
			// Set delivery time
			order.setEstimatedTimeInMinute(new Random().nextInt(60));
		} else {
			System.out.println("Order failed with status " + result);
			order.setOredrStatus(Order.OrderStatus.REJECTED);
		}
		// save to repo to preserve effects.
		orderRepository.save(order);
		return true;
	}

}
