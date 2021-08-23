package com.demo.payment.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.payment.service.entity.OrderEvent;

@FeignClient("order-service")
public interface OrderServiceClient {

	@PostMapping("/orders/order/event")
	public void processOrderEvent(OrderEvent orderEvent);

}