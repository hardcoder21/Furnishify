package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.modal.*;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public Category findByName(String name);

	@Query("Select c from Category c where c.name=:name ")
	public Category findByNameAndParant(@Param("name") String name);
}
