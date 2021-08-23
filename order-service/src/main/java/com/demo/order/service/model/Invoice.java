package com.demo.order.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Invoice {

	private Integer invoiceId;

	private double price;

	@JsonCreator
	public Invoice(@JsonProperty("invoiceId") Integer invoiceId, @JsonProperty("price") double price) {
		this.invoiceId = invoiceId;
		this.price = price;
	}
}
