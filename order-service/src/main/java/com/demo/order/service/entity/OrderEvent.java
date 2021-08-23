package com.demo.order.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.demo.order.service.enums.OrderEventType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "order_events")
@Entity
@Data
@NoArgsConstructor
public class OrderEvent implements Serializable {

	private static final long serialVersionUID = 7885474350927807225L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_event_id")
	private Integer orderEventId;

	@Column(name = "event_type")
	private OrderEventType eventType;

	@Column(name = "order_Id")
	private Integer orderId;

	@JsonCreator
	public OrderEvent(@JsonProperty("eventType") OrderEventType eventType, @JsonProperty("orderId") Integer orderId) {
		this.eventType = eventType;
		this.orderId = orderId;
	}
}
