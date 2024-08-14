package com.interverse.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="events")
public class Event {
	
	@Column(insertable=false, updatable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer source;
	
	private String location;
	
	private String eventPhoto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="clubId")
	private Club club;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	private User creator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime added;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)	
	private EventDetail eventDetail;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="eventId")
	private List<EventPhoto> evenphoto;
	
	
	@PrePersist
	protected void onCreate() {
		added = LocalDateTime.now();
	}
	
}	

