package com.interverse.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_post_photos")
@Getter
@Setter
@NoArgsConstructor
public class PostPhoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JoinColumn(name = "post_id")
	@ManyToOne
	@JsonBackReference("post-photos")
	private UserPost userPost;
	
	@Column(name="name")
	private String name;

	@Column(name = "url")
	private String url;

}
