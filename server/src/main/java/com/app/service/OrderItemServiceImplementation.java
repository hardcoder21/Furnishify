package com.app.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.repository.OrderItemRepository;
import com.app.modal.OrderItem;

@Service
@Transactional
public class OrderItemServiceImplementation implements OrderItemService {

	private OrderItemRepository orderItemRepository;
	public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
		this.orderItemRepository=orderItemRepository;
	}
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}

}
