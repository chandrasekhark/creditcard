package com.sapient.creditcard.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(name = "card_number")
	private Long cardNumber;
	private BigDecimal balance;
	@Column(name = "credit_limit")
	private BigDecimal limit;
	
	public CreditCard() {
		
	}
	
	public CreditCard(String name, Long cardNumber, BigDecimal balance, BigDecimal limit) {
		super();
		this.name = name;
		this.cardNumber = cardNumber;
		this.balance = balance;
		this.limit = limit;
	}

	public CreditCard(Integer id, String name, Long cardNumber, BigDecimal balance, BigDecimal limit) {
		super();
		this.id = id;
		this.name = name;
		this.cardNumber = cardNumber;
		this.balance = balance;
		this.limit = limit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

}
