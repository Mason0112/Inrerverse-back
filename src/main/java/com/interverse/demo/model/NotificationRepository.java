package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
	@Query("from Notification where receiver.id = :id")
	List<Notification> findByReceiverId(@Param("id") Integer userId);

}