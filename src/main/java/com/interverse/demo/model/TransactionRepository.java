package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	List<Transaction> findByUserId(Integer userId);
	
	@Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.id = :userId and t.status = 1 or t.status = 2")
    Long sumAmountsByUserId(@Param("userId") Integer userId);
	

}
