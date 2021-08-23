package com.demo.order.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.order.service.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
