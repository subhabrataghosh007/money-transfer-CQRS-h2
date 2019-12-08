package com.payment.service;

import java.util.List;

import com.payment.dto.MoneyTransfer;
import com.payment.model.MoneyTransferModel;

public interface TransactionService {

	public MoneyTransferModel transfer(MoneyTransfer trx);

	public List<MoneyTransferModel> transactions();
	public MoneyTransferModel getByTransactionId(String id);

	public List<MoneyTransferModel> getTransactionsBySender(String sender);
	
}
