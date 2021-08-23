package com.demo.payment.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.payment.service.entity.CardInfo;
import com.demo.payment.service.entity.Payment;
import com.demo.payment.service.model.Invoice;
import com.demo.payment.service.service.PaymentService;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "Payment Gateway", description = "Do Payment")
public class PaymentServiceRestController {

	private PaymentService paymentService;
	private final String defaultPageNum = "0";
	private final String defaultPageSize = "2";

	@Autowired
	public PaymentServiceRestController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@DeleteMapping("/payment")
	public void deleteAll() {
		this.paymentService.deleteAll();
	}

	// Notice: POST method is creating a payment, it does not mean that the
	// payment will be fulfilled.
	@SuppressWarnings("rawtypes")
	@PostMapping("/payment")
	public ResponseEntity createPayment(@RequestBody Invoice invoice) {
		System.out.println("Payment service received invoice from " + invoice);
		if (this.paymentService.getPaymentByOrderId(invoice.getOrderId()) != null) {
			System.out.println("Multiple payments for the same order not allowed");
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		this.paymentService.createPayment(invoice);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/payment")
	Page<Payment> findAll(@RequestParam(name = "page", required = false, defaultValue = defaultPageNum) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = defaultPageSize) Integer size) {
		return this.paymentService.findAll(PageRequest.of(page, size));
	}

	@GetMapping("/payment/{paymentId}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable String paymentId) {
		return new ResponseEntity<>(this.paymentService.getPaymentById(paymentId), HttpStatus.OK);
	}

	@PutMapping("/payment/{paymentId}")
	public HttpStatus Payment(@PathVariable String paymentId, @RequestBody CardInfo info) {
		Payment payment = paymentService.getPaymentById(paymentId);
		paymentService.fulfillPayment(payment, info);
		return HttpStatus.OK;
	}

}
