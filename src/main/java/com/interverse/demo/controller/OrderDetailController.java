package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.OrderDetailDTO;
import com.interverse.demo.model.Order;
import com.interverse.demo.model.OrderDetail;
import com.interverse.demo.model.OrderDetailId;
import com.interverse.demo.model.Product;
import com.interverse.demo.service.OrderDetailService;
import com.interverse.demo.service.OrderService;
import com.interverse.demo.service.ProductService;

@RestController
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	
	@PostMapping("/orderDetail/addpost")
	public String createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO ) {
		
		Product productById = productService.findProductById(orderDetailDTO.getProductId());
		Order orderById = orderService.findOrderById(orderDetailDTO.getOrderId());
		OrderDetail orderDetail = new OrderDetail();
		OrderDetailId orderDetailId = new OrderDetailId();
		orderDetailId.setOrdersId(orderDetailDTO.getOrderId());
		orderDetailId.setProductsId(orderDetailDTO.getProductId());
		orderDetail.setOrderDetailId(orderDetailId);
		orderDetail.setQuantity(orderDetailDTO.getVol());
		orderDetail.setOrders(orderById);
		orderDetail.setProducts(productById);
		orderDetailService.addToOrder(orderDetail);
		
		return "ok";
		
	}
	
	
}
