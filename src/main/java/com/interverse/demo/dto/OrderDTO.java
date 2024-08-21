package com.interverse.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

	 private Integer id;
	    private Integer paymentMethod;
	    private Integer status;
	    private LocalDateTime added;
	    private Integer userId;
	    private Integer totalAmount;
	    

	    public void calculateTotalAmount(List<OrderDetailDTO> details) {
	        this.totalAmount = details.stream()
	                .mapToInt(OrderDetailDTO::getSubtotal)
	                .sum();
	    }
}
