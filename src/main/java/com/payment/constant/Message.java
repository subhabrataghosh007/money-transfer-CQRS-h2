package com.payment.constant;

public enum Message {
	
	SUCCESS("success"),
	FAILED("failed"),
	PHONE_NUMBER_MISSING("Phone Number Missing"),
	PHONE_NUMBER_NOT_FOUND("Phone Number Not Fount"),
	DUPLICATE_WALLET("Wallet already available"),
	WALLET_SUCCESS("Wallet Successfully Created"),
	ADD_MONEY_SUCCESS(" amount added successfully in Wallet: "),
	TRANSACTION_NOT_FOUND("No Transaction Found by ID: "),;
	
	
	private String message;
	
	Message(String message) {
		this.message  = message;
	}
	
	public String getMessage() {
		return message;
	}

}
