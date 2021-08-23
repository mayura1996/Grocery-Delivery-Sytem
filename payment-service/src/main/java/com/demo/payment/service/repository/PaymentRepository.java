package com.demo.payment.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.payment.service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

	Payment findByOrderId(String orderId);
}
