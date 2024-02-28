package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.request.CreateProductRequest;
import com.app.response.DisplayProductResponse;
import com.app.exception.ProductException;
import com.app.modal.*;



public interface ProductService {
	
	// only for admin
	public Product createProduct(CreateProductRequest req) throws ProductException;
	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId,Product product)throws ProductException;
	
	public List<DisplayProductResponse> getAllProducts();
	
	// for user and admin both
	public DisplayProductResponse findProductById(Long id) throws ProductException;
	
	public Product findProductByIdMan(Long id) throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public List<DisplayProductResponse> searchProduct(String query);
	
//	public List<Product> getAllProduct(List<String>colors,List<String>sizes,int minPrice, int maxPrice,int minDiscount, String category, String sort,int pageNumber, int pageSize);
	public Page<Product> getAllProduct(String category, List<String>colors, String stock, Integer pageNumber, Integer pageSize);
	
	
	
	

}
