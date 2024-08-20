package com.interverse.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.EventParticipant;
import com.interverse.demo.model.EventParticipantRepository;

@Service
public class EventParticipantService {
	
	@Autowired
	private EventParticipantRepository eptRepo;
	
	public EventParticipant saveEpt(EventParticipant ept) {
		return eptRepo.save(ept);
	}
	
	//活動裡尋找所有參與者
	public List<EventParticipant> findAllEventParticipant(Integer EventId) {
		return eptRepo.findByEventParticipantIdEventId(EventId);
	}
	
	
}
