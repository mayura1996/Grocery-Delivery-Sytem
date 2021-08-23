package com.demo.payment.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.demo.payment.service.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "order_events")
@Entity
@Data
@NoArgsConstructor
public class OrderEvent implements Serializable {

	private static final long serialVersionUID = 7535317971755197188L;

	public enum OrderEventType {
		// This order has been created and no responses has been received.
		PROCESSING,
		// This order has been successfully created.
		SUCCEED,
		// This order has failed to be processed.
		FAILURE;
	}

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_event_id")
	private String orderEventId;

	@Column(name = "event_Type")
	private OrderEventType eventType;

	@Column(name = "order_id")
	private String orderId;

	public OrderEvent(Payment payment) {
		this.orderId = payment.getOrderId();
		if (payment.getPaymentStatus().equals(PaymentStatus.APPROVED)) {
			this.eventType = OrderEventType.SUCCEED;
		} else if (payment.getPaymentStatus().equals(PaymentStatus.DECLINED)) {
			this.eventType = OrderEventType.FAILURE;
		} else {
			throw new IllegalArgumentException("Illegal payment status " + payment.getPaymentStatus());
		}
	}
}
