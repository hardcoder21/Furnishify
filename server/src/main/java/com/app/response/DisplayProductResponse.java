package com.app.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//import com.app.modal.Rating;

import com.app.modal.Size;

public class DisplayProductResponse {
	
    private Long id;

  
    private String title;

 
    private String description;

 
    private int price;

 
  
    
    private int quantity;

  
    private String brand;


    private String color;

    
//    private Set<Size> sizes=new HashSet<>();

    
    private String imageUrl;

    
//    private List<Rating>ratings=new ArrayList<>();
    
    
//    private List<Review>reviews=new ArrayList<>();

 
    private int numRatings;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}




	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}





	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}





	public int getNumRatings() {
		return numRatings;
	}


	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}
    
    

}
