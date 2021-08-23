package com.demo.order.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.order.service.entity.Order;
import com.demo.order.service.entity.OrderEvent;
import com.demo.order.service.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "Order Service", description = "To Make Order for the available Restaurants")
public class OrderServiceRestController {

	private final String defaultPageNum = "0";
	private final String defaultPageSize = "2";

	@Autowired
	private OrderService orderService;

	Logger logger = LoggerFactory.getLogger(OrderServiceRestController.class);

	@GetMapping("/order")
	@ApiOperation(value = "View All Placed Orders Details")
	Page<Order> findAll(@RequestParam(name = "page", required = false, defaultValue = defaultPageNum) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = defaultPageSize) Integer size) {
		return this.orderService.findAll(PageRequest.of(page, size));
	}

	@GetMapping("/order/{id}")
	@ApiOperation(value = "View Particular Placed Orders details with Order Id")
	Order findById(@PathVariable(value = "id") Integer id) {
		return orderService.findById(id);
	}

	@PostMapping("/order")
	@ApiOperation(value = "Place Order")
	HttpStatus CreateOrder(@RequestBody Order payload) {
		this.orderService.createOrder(payload);
		return HttpStatus.OK;
	}

	@DeleteMapping("/order")
	void purge() {
		this.orderService.deleteAll();
	}

	@DeleteMapping("/order/{id}")
	@ApiIgnore
	void deleteByid(@PathVariable(value = "id") Integer id) {
		orderService.deleteById(id);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/order/event")
	@ApiOperation(value = "To Process the Order Events")
	public ResponseEntity processOrderEvent(@RequestBody OrderEvent orderEvent) {
		orderService.processOrderEvent(orderEvent);
		return new ResponseEntity(HttpStatus.CREATED);
	}
}
