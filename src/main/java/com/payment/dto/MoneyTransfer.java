package com.payment.dto;

import static com.payment.constant.ValidationMessage.SENDER_EMPTY;
import static com.payment.constant.ValidationMessage.SENDER_VALIDATION;
import static com.payment.constant.ValidationMessage.AMOUNT_EMPTY;
import static com.payment.constant.ValidationMessage.AMOUNT_ERROR;
import static com.payment.constant.ValidationMessage.RECEIVER_EMPTY;
import static com.payment.constant.ValidationMessage.RECEIVER_VALIDATION;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

final public class MoneyTransfer {

	@NotNull(message = SENDER_EMPTY)
	@Pattern(regexp = "^[1-9]\\d{9}$", message = SENDER_VALIDATION)
	private String sender;
	
	@NotNull(message = RECEIVER_EMPTY)
	@Pattern(regexp = "^[1-9]\\d{9}$", message = RECEIVER_VALIDATION)
	private String receiver;
	
	@NotNull(message = AMOUNT_EMPTY)
	@Min(value = 1, message = AMOUNT_ERROR)
	private BigDecimal amount;
	
	private String tag;

	public MoneyTransfer() {}
	
	public MoneyTransfer(String sender, String receiver, String amount, String tag) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = new BigDecimal(amount);
		this.tag = tag;
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getTag() {
		return tag;
	}
}