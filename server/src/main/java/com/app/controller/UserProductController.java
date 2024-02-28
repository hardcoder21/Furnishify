package com.app.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.ProductException;
import com.app.modal.Product;
import com.app.response.DisplayProductResponse;
import com.app.service.ProductService;

@RestController
@RequestMapping("/api")
public class UserProductController {
	
	private ProductService productService;
	
	public UserProductController(ProductService productService) {
		this.productService=productService;
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam(required = false) String category,
			@RequestParam(required = false) List<String>color,  @RequestParam(required = false) String sort, 
			@RequestParam(required = false) String stock, @RequestParam Integer pageNumber,@RequestParam Integer pageSize){

		
		Page<Product> res= productService.getAllProduct(category, color, stock,pageNumber,pageSize);
		
		System.out.println("complete products");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		
	}
	

	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<DisplayProductResponse> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
		
		DisplayProductResponse product=productService.findProductById(productId);
		
		return new ResponseEntity<DisplayProductResponse>(product,HttpStatus.ACCEPTED);
	}

	@GetMapping("/products/search")
	public ResponseEntity<List<DisplayProductResponse>> searchProductHandler(@RequestParam String q){
		
		List<DisplayProductResponse> products=productService.searchProduct(q);
		
		return new ResponseEntity<List<DisplayProductResponse>>(products,HttpStatus.OK);
		
	}
}
