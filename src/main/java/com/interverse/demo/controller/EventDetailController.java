package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.EventDetail;
import com.interverse.demo.service.EventDetailService;

@RestController
@RequestMapping("/eventDetail")
public class EventDetailController {
	
	@Autowired
	private EventDetailService edService;
	
	@PostMapping
	public EventDetail createED(@RequestBody EventDetail eventDetail) {
		return edService.saveED(eventDetail);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEventDetailById(@PathVariable Integer id){
		EventDetail result = edService.findEDById(id);
		
		if(result !=null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.status(HttpStatus.OK).body("無此ID");
	
	}
}
