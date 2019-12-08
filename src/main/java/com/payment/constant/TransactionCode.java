package com.payment.constant;

public enum TransactionCode {

	APPROVED("00", "Transaction Approved Successfully"),
	InsifficientAmount("05", "Insufficient amount. Transaction declined"),
	InvalidAmount("13", " Check the transaction information and try processing the transaction again."),
	InvalidCardNumber("14", "The phone number has declined the transaction as the phone number is incorrectly entered, or does not exist"),
	UnacceptableTransactionFee("23", "An unspecified bank error has occurred");

	private String code;
	private String message;

	TransactionCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
