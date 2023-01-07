package com.sapient.creditcard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sapient.creditcard.model.CreditCard;

@Service
public interface CreditCardService {

	public CreditCard createCreditCard(CreditCard creditCard);
	
	public List<CreditCard> getCreditCards();
	
}
