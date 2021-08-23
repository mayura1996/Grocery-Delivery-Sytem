package com.demo.order.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem implements Serializable {

	private static final long serialVersionUID = -6580760505006247102L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Integer orderItemId;

	// name of this item
	@Column(name = "item_name")
	private String itemName;

	// id of the restaurant
	@Column(name = "restaurant_id")
	private String restaurantId;

	// special instruction for this item
	@Column(name = "item_instruction")
	private String itemInstruction;

	// price of this item
	@Column(name = "item_price")
	private double itemPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "or_item_id", referencedColumnName = "order_id")
	private Order order;

	@JsonCreator
	public OrderItem(@JsonProperty("itemName") String name, @JsonProperty("restaurantId") String providerId,
			@JsonProperty("itemInstruction") String notice, @JsonProperty("itemPrice") double price) {
		this.itemName = name;
		this.itemInstruction = notice;
		this.restaurantId = providerId;
		this.itemPrice = price;
	}
}
