package com.interverse.demo.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
	 	private Integer userId;
	    private Integer productId;
	    private Integer vol;
	    private String productName;
}
