package com.demo.payment.service.serviceImpl;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.payment.service.entity.CardInfo;
import com.demo.payment.service.entity.OrderEvent;
import com.demo.payment.service.entity.Payment;
import com.demo.payment.service.enums.PaymentStatus;
import com.demo.payment.service.feignClient.OrderServiceClient;
import com.demo.payment.service.model.Invoice;
import com.demo.payment.service.repository.PaymentRepository;
import com.demo.payment.service.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	OrderServiceClient orderServiceClient;

	private PaymentRepository paymentRepository;

	@Autowired
	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Override
	public Payment getPaymentByOrderId(String id) {
		return paymentRepository.findByOrderId(id);
	}

	@Override
	public Payment getPaymentById(String id) {
		return paymentRepository.findById(id).get();
	}

	@Override
	public Page<Payment> findAll(Pageable pageable) {
		return paymentRepository.findAll(pageable);
	}

	@Override
	public void deleteAll() {
		paymentRepository.deleteAll();
	}

	@Override
	public Payment createPayment(Invoice invoice) {
		Payment payment = new Payment(invoice.getOrderId());
		paymentRepository.save(payment);
		System.out.println("Payment created with ID " + payment.getPaymentId() + " for order " + payment.getOrderId());
		return payment;
	}

	@Override
	public void fulfillPayment(Payment payment, CardInfo info) {
		try {
			// validate card
			if (!validateCard(info)) {
				System.out.println("Card information not valid, will reject now");
				payment.setPaymentStatus(PaymentStatus.DECLINED);
			} else {
				// determine the payment status randomly
				int i = new Random().nextInt(2);
				if (i == 0) {
					System.out.println("Payment succeeded");
					payment.setPaymentStatus(PaymentStatus.APPROVED);
				} else {
					System.out.println("Payment failed");
					payment.setPaymentStatus(PaymentStatus.DECLINED);
				}
			}
			OrderEvent orderEvent = new OrderEvent(payment);
			orderServiceClient.processOrderEvent(orderEvent);
			System.out.println("Payment processing finished for id " + payment.getPaymentId());
			payment.setPaymentDate(new Date());
			this.paymentRepository.save(payment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validateCard(CardInfo info) {
		// In reality, it will be put to card service
		// Here we are returning dummy values.
		return info.isValid();
	}

}
