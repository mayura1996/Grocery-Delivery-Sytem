package com.demo.order.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "orders")
@Entity
@Data
@NoArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID = 7060028003264765628L;

	public enum OrderStatus {
		PENDING, APPROVED, REJECTED;
	}

	@Id
	@Column(name = "order_id")
	private Integer orderId;

	// Name of the customer
	@Column(name = "customer_name")
	private String customerName;

	// Special Instructions for this order
	@Column(name = "order_instruction")
	private String orderInstruction;

	// Delivery Address;
	@Column(name = "address")
	private String address;

	// Total Money value of this order;
	@Column(name = "total_price")
	private double totalPrice;

	// Status of this order
	@Column(name = "order_status")
	OrderStatus oredrStatus;

	// Content(All Items) of this order
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<OrderItem> orderItems;

	// estimated delivery time in minutes
	@Column(name = "estimated_time")
	private double estimatedTimeInMinute;

	@JsonCreator
	public Order(@JsonProperty("customerName") String customerName,
			@JsonProperty("orderInstruction") String instruction, @JsonProperty("address") String address,
			@JsonProperty("orderItems") List<OrderItem> orderItems) {
		this.customerName = customerName;
		this.orderInstruction = instruction;
		this.address = address;
		this.orderItems = orderItems;
		this.totalPrice = calculateTotal();
		this.oredrStatus = OrderStatus.PENDING;
		// initial value: 24 hours = 90 minutes
		this.estimatedTimeInMinute = 90;
	}

	private double calculateTotal() {
		double ret = 0.0;
		for (OrderItem item : orderItems) {
			ret += item.getItemPrice();
		}
		return ret;
	}

}
