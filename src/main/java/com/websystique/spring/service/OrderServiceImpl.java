package com.websystique.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websystique.spring.model.ProductOrder;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Autowired
	MailService mailService;

	@Override
	public void sendOrderConfirmation(ProductOrder productOrder) {
		mailService.sendEmail(productOrder);
	}
	
}
