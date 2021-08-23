package com.demo.order.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.order.service.model.Invoice;

@FeignClient("payment-service")
public interface PaymentServiceClient {

	@PostMapping("/payments/payment")
	public ResponseEntity<Invoice> createPayment(Invoice invoice);

}