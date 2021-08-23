package com.demo.gateway.server.fallback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

	@GetMapping("/orderServiceFallback")
	public Mono<String> userServiceFallback() {
		return Mono.just("Order Service is taking too long to respond..!!Try Again Later");
	}

	@GetMapping("/paymentServiceFallback")
	public Mono<String> paymentServiceFallback() {
		return Mono.just("Payment Order Service is taking too long to respond..!!Try Again Later");
	}

	@GetMapping("/restaurantServiceFallback")
	public Mono<String> restaurantServiceFallback() {
		return Mono.just("Restaurant Service is taking too long to respond..!!Try Again Later");
	}
}
