package com.demo.restaurant.service.entity;

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

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItem implements Serializable {

	private static final long serialVersionUID = -3598583235467998700L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_item_id")
	private Integer menuItemId;

	// Name of the item
	@Column(name = "item_name")
	private String itemName;

	// introduction of the item
	@Column(name = "item_description")
	private String itemDescription;

	// price of the item;
	@Column(name = "item_price")
	private double itemPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "restaurant_menu_item_id", referencedColumnName = "restaurant_id")
	private Restaurant restaurant;

	@JsonCreator
	public MenuItem(@JsonProperty("itemName") String name, @JsonProperty("itemDescription") String description,
			@JsonProperty("itemPrice") double price) {
		this.itemName = name;
		this.itemDescription = description;
		this.itemPrice = price;
	}
}
