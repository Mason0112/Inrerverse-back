package com.interverse.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private String accountNumber;
	private String password;
	private String email;
	private String nickname;
	
	private String phoneNumber;
	private String country;
	private String city;
	private LocalDate birthday;
	private String gender;
	private String photo;
	private String bio;
}
