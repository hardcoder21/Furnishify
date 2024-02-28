package com.app.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.modal.Address;
import com.app.modal.OrderItem;
import com.app.modal.PaymentDetails;
import com.app.modal.User;
import com.app.user.domain.OrderStatus;

public class OrderDisplayDTO {
	
	
    private Long id;

   
    private String orderId;
  
   
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private User user;


   

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    
    private Address shippingAddress;

   
    private PaymentDetails paymentDetails=new PaymentDetails();

    private double totalPrice;

    private OrderStatus orderStatus;
    
    private int totalItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
   
    
    
    


}
