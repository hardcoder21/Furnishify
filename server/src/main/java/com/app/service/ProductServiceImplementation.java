package com.app.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exception.ProductException;
import com.app.modal.CartItem;
import com.app.modal.Category;
import com.app.modal.OrderItem;
import com.app.modal.Product;

import com.app.repository.CartItemRepository;
import com.app.repository.CategoryRepository;
import com.app.repository.OrderItemRepository;
import com.app.repository.ProductRepository;
import com.app.request.CreateProductRequest;
import com.app.response.DisplayProductResponse;

@Service
@Transactional
public class ProductServiceImplementation implements ProductService {
	
	private ProductRepository productRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;
	private OrderItemRepository orderItemRepository;
	
	private CartItemRepository cartItemRepository;
	
	public ProductServiceImplementation(ProductRepository productRepository,UserService userService,CategoryRepository categoryRepository,OrderItemRepository orderItemRepository,CartItemRepository cartItemRepository) {
		this.productRepository=productRepository;
		this.userService=userService;
		this.categoryRepository=categoryRepository;
		this.orderItemRepository=orderItemRepository;
		this.cartItemRepository=cartItemRepository;
	}
	

	@Override
	public Product createProduct(CreateProductRequest req) {
		
		
		
		
		Product product=new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		//product.setSizes(req.getSize());
//		Set<Size> bro=req.getSize();
//		for(Size s:bro)
//		{
//			product.getSizes().add(s);
//		}
		product.setQuantity(req.getQuantity());
		
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct= productRepository.save(product);
		
		System.out.println("products - "+product);
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		
		
		Product product=findProductByIdMan(productId);
		CartItem cartItem=cartItemRepository.removeCartItemOfAdmin(productId);
		if(cartItem!=null)
		{
			cartItemRepository.delete(cartItem);
		}
		
		
		System.out.println(product.getId());
		
		List<OrderItem> orderItems = orderItemRepository.findBoi(product.getId());
		int count=0;
		for(OrderItem m:orderItems)
		{
			product.getSizes().clear();
			
//			product.getReviews().clear();
			product.getOrderItems().clear();
			
			m.setProduct(null);
			count++;
			//orderItemRepository.delete(m);
		}
		System.out.println(count);
	
		
		
		
		//orderItemRepository.deleteAll(orderItems);
	
		
		
		
		System.out.println("delete product "+product.getId()+" - "+productId);
		//product.getSizes().clear();
//		productRepository.save(product);
//		product.getCategory().
		productRepository.delete(product);
		
		return "Product deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId,Product req) throws ProductException {
		Product product=findProductByIdMan(productId);
		
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		if(req.getDescription()!=null) {
			product.setDescription(req.getDescription());
		}
		if(req.getTitle()!=null) {
			product.setTitle(req.getTitle());
		}
		if(req.getTitle()!=null) {
			product.setTitle(req.getTitle());
		}
		
		
			
		
		return productRepository.save(product);
	}

	@Override
	public List<DisplayProductResponse> getAllProducts() {
		List<Product> prod= productRepository.findAll();
		List<DisplayProductResponse> list=new ArrayList<DisplayProductResponse>();
		for (Product p : prod) {
			DisplayProductResponse data=new DisplayProductResponse();
			data.setId(p.getId());
			data.setBrand(p.getBrand());
			data.setTitle(p.getTitle());
			data.setPrice(p.getPrice());
			data.setDescription(p.getDescription());
			data.setQuantity(p.getQuantity());
			data.setImageUrl(p.getImageUrl());
			data.setColor(p.getColor());
//			Set<Size> s=p.getSizes();
//			for(Size hmm:s) {
//				data.getSizes().add(hmm);
//			}
			
			//data.setSizes(p.getSizes());
			
			list.add(data);
		} 
		return list;
		
	}
        
	@Override
	public DisplayProductResponse findProductById(Long id) throws ProductException {
		Optional<Product> opt=productRepository.findById(id);
		//Product opt=productRepository.findByIdWithRatings(id); 
		DisplayProductResponse data=new DisplayProductResponse();
		
		
		
		if(opt.isPresent()) {
			 
			Product obj=opt.get();
			data.setTitle(obj.getTitle());
			data.setDescription(obj.getDescription());
			data.setPrice(obj.getPrice());
			data.setImageUrl(obj.getImageUrl());
//			Set<Size> s=obj.getSizes();
//			for(Size hmm:s)
//			{
//				data.getSizes().add(hmm);
//			}
			return data; 
		}
		throw new ProductException("product not found with id "+id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		
		System.out.println("category --- "+category);
		
		List<Product> products = productRepository.findByCategory(category);
		
		return products;
	}

	@Override
	public List<DisplayProductResponse> searchProduct(String query) {
		List<Product> products=productRepository.searchProduct(query);
		List<DisplayProductResponse> list=new ArrayList<DisplayProductResponse>();
		
		for (Product p : products) {
			DisplayProductResponse data=new DisplayProductResponse();
			data.setId(p.getId());
			data.setBrand(p.getBrand());
			data.setTitle(p.getTitle());
			data.setPrice(p.getPrice());
			data.setDescription(p.getDescription());
			data.setQuantity(p.getQuantity());
			data.setImageUrl(p.getImageUrl());
			data.setColor(p.getColor());
			//data.setSizes(p.getSizes());
			
			list.add(data);
		} 
		
		
		return list;
	}
	
	
	
	



	
	
	@Override
	public Product findProductByIdMan(Long id) throws ProductException {
		
		Optional<Product> opt=productRepository.findById(id);
		
		if(opt.isPresent()) {
			
			
			return opt.get(); 
		}
		throw new ProductException("product not found with id "+id);
	}


	@Override
	public Page<Product> getAllProduct(String category, List<String>colors, 
			 String stock, Integer pageNumber, Integer pageSize ) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize); 
		
		List<Product> products = productRepository.filterProducts(category);
		
		
		if (!colors.isEmpty()) {
			products = products.stream()
			        .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
			        .collect(Collectors.toList());
		
		
		} 

		if(stock!=null) {

			if(stock.equals("in_stock")) {
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}
			else if (stock.equals("out_of_stock")) {
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());				
			}
				
					
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
	    return filteredProducts; // If color list is empty, do nothing and return all products
		
		
	}

}
