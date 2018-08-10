package com.websystique.spring.service;

import com.websystique.spring.model.ProductOrder;

public interface OrderService {

	public void sendOrderConfirmation(ProductOrder productOrder);
	
}
