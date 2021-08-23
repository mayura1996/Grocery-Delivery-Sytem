package com.demo.order.service.enums;

public enum OrderEventType {
	// This order has been created and no responses has been received.
	PROCESSING,
	// This order has been successfully created.
	SUCCEED,
	// This order has failed to be processed.
	FAILURE;
}
