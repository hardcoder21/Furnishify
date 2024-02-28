package com.app.service;

import java.util.List;

import com.app.exception.OrderException;
import com.app.modal.Address;
import com.app.modal.Order;
import com.app.modal.User;
import com.app.request.AddressDTORequest;
import com.app.response.OrderDTO;
import com.app.response.OrderDisplayDTO;

public interface OrderService {
	
	public OrderDTO createOrder(User user, AddressDTORequest shippingAdress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public OrderDisplayDTO findOrderByIdBro(Long orderId) throws OrderException;
	
	public List<OrderDisplayDTO> usersOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId)throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order cancledOrder(Long orderId) throws OrderException;
	
	public List<OrderDisplayDTO>getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
	
}
