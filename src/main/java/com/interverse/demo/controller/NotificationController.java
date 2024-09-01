package com.interverse.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.NotificationDto;
import com.interverse.demo.model.Notification;
import com.interverse.demo.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	
	@Autowired
	private NotificationService notifService;
	
	@PostMapping("/add")
	public ResponseEntity<NotificationDto> addNotification(@RequestBody Notification notification){
		
		NotificationDto notificationDto = notifService.addNotification(notification);
		return ResponseEntity.status(HttpStatus.CREATED).body(notificationDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<NotificationDto>> findMyNotification(@PathVariable Integer id) {
		
		List<NotificationDto> myNotificationList = notifService.findMyNotification(id);
		return ResponseEntity.ok(myNotificationList);
	}
}
