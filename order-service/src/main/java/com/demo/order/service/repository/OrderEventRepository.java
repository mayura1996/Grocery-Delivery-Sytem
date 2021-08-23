package com.demo.order.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.order.service.entity.OrderEvent;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Integer> {
}
