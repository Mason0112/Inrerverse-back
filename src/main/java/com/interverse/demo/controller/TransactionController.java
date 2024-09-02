package com.interverse.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.StripeChargeRequestDto;
import com.interverse.demo.dto.TransactionDto;
import com.interverse.demo.model.Transaction;
import com.interverse.demo.model.User;
import com.interverse.demo.service.TransactionService;
import com.interverse.demo.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transService;

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public ResponseEntity<TransactionDto> addTransaction(@RequestBody Transaction transaction) {
		
		Integer userId = transaction.getUser().getId();
		
		User user = userService.findUserById(userId);
		Long currentBalance = user.getWalletBalance();

		Long transAmount = transaction.getAmount();

		if ((currentBalance + transAmount) < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		TransactionDto transactionDto = transService.addTransaction(transaction);
		userService.updateUserWalletBalance(userId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
	}
	
	@PutMapping("/switch-status")
	public ResponseEntity<TransactionDto> switchStatusToCompleted(@RequestBody Transaction transaction) {
		TransactionDto transactionDto = transService.updateStatusToCompleted(transaction);
		
		Integer userId = transaction.getUser().getId();
		userService.updateUserWalletBalance(userId);
		
		return ResponseEntity.ok(transactionDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<TransactionDto>> findMyTransactcion(@PathVariable Integer id) {

		List<TransactionDto> myTransactionList = transService.findMyTransaction(id);
		return ResponseEntity.ok(myTransactionList);
	}

	@GetMapping("/all")
	public ResponseEntity<List<TransactionDto>> findAllTransaction() {

		List<TransactionDto> allTransaction = transService.findAllTransaction();
		return ResponseEntity.ok(allTransaction);
	}
	
    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@RequestBody StripeChargeRequestDto chargeRequest) {
        try {
            ChargeCreateParams createParams = new ChargeCreateParams.Builder()
                    .setAmount(chargeRequest.getAmount())
                    .setCurrency("TWD")
                    .setSource(chargeRequest.getToken())
                    .build();

            Charge charge = Charge.create(createParams);

            
            return ResponseEntity.ok("Payment successful: " + charge.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + e.getMessage());
        }
    }

}
