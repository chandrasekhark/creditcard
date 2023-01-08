package com.sapient.creditcard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.creditcard.exception.IncorrectCardNumber;
import com.sapient.creditcard.model.CreditCard;
import com.sapient.creditcard.repository.CreditCardRepository;
import com.sapient.creditcard.service.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository;
	
	/**
	 * This method will create a new credit card.
	 */
	@Override
	public CreditCard createCreditCard(CreditCard creditCard) {
		if(validateCreditCard(creditCard.getCardNumber())) {
			return creditCardRepository.save(creditCard);
		}
		else {
			throw new IncorrectCardNumber("Invalid credit card number : "+creditCard.getCardNumber());
		}
		
	}

	/**
	 * Here we are checking the entered credit card number is valid. Luhn 10 algorithm logic.
	 * @param cardNumber
	 * @return
	 */
	private boolean validateCreditCard(Long cardNumber) {
		String strCardNumber = cardNumber.toString();
		int[] a = { strCardNumber.toString().length() % 2 == 0 ? 1 : 2 }; // 1 if length even, 2 otherwise
		return strCardNumber.chars().map(i -> i - '0') // convert to the int equivalent
				.map(n -> n * (a[0] = a[0] == 1 ? 2 : 1)) // multiply by 1, 2 alternating
				.map(n -> n > 9 ? n - 9 : n) // handle sum of digits
				.sum() % 10 == 0;
	}

	@Override
	public List<CreditCard> getCreditCards() {
		return creditCardRepository.findAll();
	}

}
