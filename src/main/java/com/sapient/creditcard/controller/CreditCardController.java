package com.sapient.creditcard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.creditcard.model.CreditCard;
import com.sapient.creditcard.service.CreditCardService;

@RestController
@CrossOrigin(origins = "*")
public class CreditCardController {

	@Autowired
	private CreditCardService creditCardService;
	
	/**
	 * Create a new create card using this API
	 * @param creditCard
	 * @return
	 */
	@PostMapping(value = "/creditcard")
	public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) {
		return new ResponseEntity<CreditCard>(creditCardService.createCreditCard(creditCard), HttpStatus.CREATED);
	}
	
	/**
	 * Get all the credit cards
	 * @return
	 */
	@GetMapping(value = "/creditcards")
	public ResponseEntity<List<CreditCard>> getCreditCards() {
		return new ResponseEntity<List<CreditCard>>(creditCardService.getCreditCards(), HttpStatus.OK);
	}
}
