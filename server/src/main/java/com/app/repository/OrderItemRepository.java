package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.modal.OrderItem;
import com.app.modal.Product;
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	@Query("SELECT o FROM OrderItem o where o.product.id=:productId")
	List<OrderItem> findBoi(@Param("productId") Long productId);

}
