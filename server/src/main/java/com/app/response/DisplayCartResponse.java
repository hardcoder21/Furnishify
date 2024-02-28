package com.app.response;

import java.util.HashSet;
import java.util.Set;

import com.app.modal.CartItem;
import com.app.modal.User;

public class DisplayCartResponse {
	
	private Long id;

    private User user;
  
    private Set<CartItem> cartItems = new HashSet<>();
    
    private double totalPrice;
     
    private int totalItem;
    
//    private int totalDiscountedPrice;
    
    private int discounte;
    
    private String imageUrl;

	public Long getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

 
    
    
    

}
