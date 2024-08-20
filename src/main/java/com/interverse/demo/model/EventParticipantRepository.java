package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantId> {
	
	List<EventParticipant> findByEventParticipantIdEventId(Integer eventId);
}
