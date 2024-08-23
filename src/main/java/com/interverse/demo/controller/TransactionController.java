package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.TransactionDto;
import com.interverse.demo.model.Transaction;
import com.interverse.demo.service.TransactionService;
import com.interverse.demo.service.UserService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transService;

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public ResponseEntity<TransactionDto> addTransaction(@RequestBody Transaction transaction) {

		TransactionDto transactionDto = transService.addTransaction(transaction);

		Integer userId = transaction.getUser().getId();
		userService.updateUserWalletBalance(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
	}

}
