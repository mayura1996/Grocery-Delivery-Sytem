package com.demo.payment.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.demo.payment.service.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "payments")
@Entity
@Data
@NoArgsConstructor
public class Payment implements Serializable {

	private static final long serialVersionUID = 56007727128950262L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private String paymentId;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "payment_status")
	private PaymentStatus paymentStatus;

	@Column(name = "payment_date")
	private Date paymentDate;

	@OneToOne
	@JoinColumn(name = "info")
	private CardInfo info;

	public Payment(String orderId) {
		this.orderId = orderId;
		this.paymentStatus = PaymentStatus.PROCESSING;
		this.paymentDate = new Date();
	}

	@JsonCreator
	public Payment(@JsonProperty("paymentId") String paymentId, @JsonProperty("orderId") String orderId,
			@JsonProperty("paymentStatus") PaymentStatus paymentStatus, @JsonProperty("paymentDate") Date paymentDate) {
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}
}
