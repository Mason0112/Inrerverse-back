package com.interverse.demo.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="eventDetails")
public class EventDetail {
	
	@Column
	@Id
	private Integer id;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Event event;
	
	private String location;
	
	private String EventPhoto;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	private LocalDateTime startTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	private LocalDateTime endTime;
	
	private String description;
	
	private Integer participantMax;
	
	private Integer participantMin;
	
	private Integer fee;
	
	
	
}
