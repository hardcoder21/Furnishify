package com.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.repository.AddressRepository;
import com.app.repository.OrderItemRepository;
import com.app.repository.OrderRepository;
import com.app.repository.UserRepository;
import com.app.request.AddressDTORequest;
import com.app.response.OrderDTO;
import com.app.response.OrderDisplayDTO;
import com.app.user.domain.OrderStatus;
import com.app.user.domain.PaymentStatus;
import com.app.exception.OrderException;
import com.app.modal.Address;
import com.app.modal.Cart;
import com.app.modal.CartItem;
import com.app.modal.Order;
import com.app.modal.OrderItem;
import com.app.modal.User;

@Service
@Transactional
public class OrderServiceImplementation implements OrderService {
	
	private OrderRepository orderRepository;
	private CartService cartService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;
	
	
	public OrderServiceImplementation(OrderRepository orderRepository,CartService cartService,
			AddressRepository addressRepository,UserRepository userRepository,
			OrderItemService orderItemService,OrderItemRepository orderItemRepository) {
		this.orderRepository=orderRepository;
		this.cartService=cartService;
		this.addressRepository=addressRepository;
		this.userRepository=userRepository;
		this.orderItemService=orderItemService;
		this.orderItemRepository=orderItemRepository;
	}

	@Override
	public OrderDTO createOrder(User user, AddressDTORequest
			shippAddress) {
		
		System.out.print("First");
		
		Address addr=new Address();
		addr.setFirstName(shippAddress.getFirstName());
		addr.setLastName(shippAddress.getLastName());
		addr.setStreetAddress(shippAddress.getStreetAddress());
		addr.setCity(shippAddress.getCity());
		addr.setState(shippAddress.getState());
		addr.setMobile(shippAddress.getMobile());
		addr.setZipCode(shippAddress.getZipCode());
		addr.setUser(user);
		//shippAddress.setUser(user);
		Address address= addressRepository.save(addr);
		user.getAddresses().add(address);
		userRepository.save(user);
		
		Cart cart=cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item: cart.getCartItems()) {
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
//			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			
			
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus(OrderStatus.PENDING);
		createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder=orderRepository.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		OrderDTO orderDto=new OrderDTO();
		orderDto.setId(savedOrder.getId());
		
		//initially returning savedOrder
		return orderDto;
		
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.PLACED);
		order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
		
		
		return orderRepository.save(order);
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		
		
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.SHIPPED);
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.DELIVERED);
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt=orderRepository.findById(orderId);
		
		if(opt.isPresent()) {
	
			
			return opt.get();
		}
		throw new OrderException("order not exist with id "+orderId);
	}
	
	

	@Override
	public OrderDisplayDTO findOrderByIdBro(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		Optional<Order> opt=orderRepository.findById(orderId);
		
		if(opt.isPresent())
		{
			Order order=opt.get();
			Long shippingAddressId=order.getShippingAddress().getId();
			Address mine=addressRepository.findOrderShippingAddress(shippingAddressId);
			OrderDisplayDTO hello=new OrderDisplayDTO();
			hello.setShippingAddress(mine);
			hello.setId(order.getId());
			hello.setOrderDate(order.getOrderDate());
			hello.setOrderStatus(order.getOrderStatus());
			hello.getPaymentDetails().setPaymentId(order.getPaymentDetails().getPaymentId());
			hello.getPaymentDetails().setPaymentMethod(order.getPaymentDetails().getPaymentMethod());
			hello.getPaymentDetails().setStatus(order.getPaymentDetails().getStatus());
			hello.setTotalPrice(order.getTotalPrice());
		
			hello.setTotalItem(order.getTotalItem());
			
			//hello.setShippingAddress(order.get);
			return hello;
	
		}
		else {
			throw new OrderException("order not exist with id "+orderId);
		}
		
	}

	@Override
	public List<OrderDisplayDTO> usersOrderHistory(Long userId) {
		List<Order> orders=orderRepository.getUsersOrders(userId);
		List<OrderDisplayDTO> list=new ArrayList<OrderDisplayDTO>();
		
		for(Order order:orders)
		{
			
			Long shippingAddressId=order.getShippingAddress().getId();
			Address mine=addressRepository.findOrderShippingAddress(shippingAddressId);
			OrderDisplayDTO hello=new OrderDisplayDTO();
			hello.setShippingAddress(mine);
			hello.setId(order.getId());
			hello.setOrderDate(order.getOrderDate());
			hello.setOrderStatus(order.getOrderStatus());
			hello.getPaymentDetails().setPaymentId(order.getPaymentDetails().getPaymentId());
			hello.getPaymentDetails().setPaymentMethod(order.getPaymentDetails().getPaymentMethod());
			hello.getPaymentDetails().setStatus(order.getPaymentDetails().getStatus());
			hello.setTotalPrice(order.getTotalPrice());
		
			hello.setTotalItem(order.getTotalItem());
			
			list.add(hello);
			
		}
		return list;
	}

	@Override
	public List<OrderDisplayDTO> getAllOrders() {
		
		List<Order> list=orderRepository.findAll();
		List<OrderDisplayDTO> mine=new ArrayList<OrderDisplayDTO>();
		for(Order each:list)
		{
			Long shippingAddressId=each.getShippingAddress().getId();
			Address mines=addressRepository.findOrderShippingAddress(shippingAddressId);
			
			OrderDisplayDTO hello=new OrderDisplayDTO();
			hello.setShippingAddress(mines);
			hello.setId(each.getId());
			System.out.println(each.getUser().getFirstName());
//			
			hello.setOrderDate(each.getOrderDate());
			hello.setOrderStatus(each.getOrderStatus());
			hello.getPaymentDetails().setPaymentId(each.getPaymentDetails().getPaymentId());
			hello.getPaymentDetails().setPaymentMethod(each.getPaymentDetails().getPaymentMethod());
			hello.setTotalPrice(each.getTotalPrice());
			hello.setTotalItem(each.getTotalItem());
			
			mine.add(hello);
			
		}
		
		return mine;
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		
		orderRepository.deleteById(orderId);
		
	}

}
