	package com.interverse.demo.model;
	
	import java.time.LocalDateTime;
	import java.util.List;
	
	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
	
	@Repository
	public interface OrderRepository extends JpaRepository<Order, Integer> {
	    List<Order> findByUsersId(Integer userId);
	    List<Order> findByStatus(Integer status);
	    List<Order> findByAddedBetween(LocalDateTime start, LocalDateTime end);
	}
