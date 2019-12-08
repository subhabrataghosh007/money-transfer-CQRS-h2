package com.payment.repository.query;

import java.math.BigDecimal;
import java.util.List;

import com.payment.model.MoneyTransferModel;

public interface TransactionQuery {

	BigDecimal getAccountBalance(String mobileNumber);

	List<MoneyTransferModel> findAllTransactions();
	
	List<MoneyTransferModel> getTransactionsBySender(String sender);

	MoneyTransferModel getTransactionsByTransactionId(String id);

}
