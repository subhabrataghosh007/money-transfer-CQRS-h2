package com.payment.constant;

public class SQLConstant {

	public static final String FIND_ALL_WALLET = "SELECT * FROM WALLETS";
	public static final String WALLET_CHECK_SQL = "SELECT COUNT(*) FROM WALLETS WHERE PHONE_NUMBER = ?";
	public static final String CREATE_WALLET_SQL = "INSERT INTO WALLETS VALUES (?, ?)";
    public static final String FETCH_BALANCE_SQL = "SELECT BALANCE FROM WALLETS WHERE PHONE_NUMBER = ?";
    public static final String DEBIT_MONEY_SQL = "UPDATE WALLETS SET BALANCE = (BALANCE - ?) WHERE PHONE_NUMBER = ?";
    public static final String CREDIT_MONEY_SQL = "UPDATE WALLETS SET BALANCE = (BALANCE + ?) WHERE PHONE_NUMBER = ?";
    
    public static final String CREATE_TRANSACTION_SQL = "INSERT INTO TRANSACTIONS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_ALL_TRANSACTION = "SELECT * FROM TRANSACTIONS";
    public static final String FIND_ALL_TRANSACTION_BY_SENDER = "SELECT * FROM TRANSACTIONS WHERE SENDER = ?";
    public static final String FIND_ALL_TRANSACTION_BY_TRANSACTIONID = "SELECT * FROM TRANSACTIONS WHERE TRANSACTIONID = ?";
    
}
