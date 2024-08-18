package com.interverse.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO  extends OrderDTO{

	
	 private Integer id; // 可能需要這個來唯一標識訂單詳情
	    private Integer orderId; // 關聯的訂單ID
	    private Integer productId;
	    private Integer quantity;
	    private Integer price;

	

	    public Integer getSubtotal() {
	        return price * quantity;
	    }
}
