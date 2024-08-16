package com.interverse.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;  

    
}
