package com.demo.payment.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.payment.service.entity.CardInfo;
import com.demo.payment.service.entity.Payment;
import com.demo.payment.service.model.Invoice;

public interface PaymentService {

	Payment getPaymentById(String id);

	Payment getPaymentByOrderId(String orderId);

	Page<Payment> findAll(Pageable pageable);

	void deleteAll();

	Payment createPayment(Invoice invoice);

	void fulfillPayment(Payment payment, CardInfo info);

}
