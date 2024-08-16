package com.interverse.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Integer id;
    private String accountNumber;
    private String email;
    private String nickname;
    private LocalDateTime added;
	
}
