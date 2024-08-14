package com.interverse.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,OrderDetailId  > {

	
}
