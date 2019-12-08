package com.payment.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.payment.constant.ValidationMessage.MOBILE_VALIDATION;
import static com.payment.constant.ValidationMessage.MOBILE_EMPTY;
import static com.payment.constant.ValidationMessage.AMOUNT_ERROR;
import static com.payment.constant.ValidationMessage.AMOUNT_EMPTY;

public class Wallet {

	@NotNull(message = MOBILE_EMPTY)
	@Pattern(regexp = "^[1-9]\\d{9}$", message = MOBILE_VALIDATION)
	private String phoneNumber;

	@NotNull(message = AMOUNT_EMPTY)
	@Min(value = 1, message = AMOUNT_ERROR)
	private BigDecimal balance;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void credit(BigDecimal amount) {
		balance = balance.add(amount);
	}

	public void debit(BigDecimal amount) {
		balance = balance.subtract(amount);
	}

}
