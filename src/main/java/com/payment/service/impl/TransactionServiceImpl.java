package com.payment.service.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import com.payment.constant.Message;
import com.payment.constant.TransactionCode;
import com.payment.dto.MoneyTransfer;
import com.payment.exception.NotFoundException;
import com.payment.exception.TransactionFailureException;
import com.payment.model.MoneyTransferModel;
import com.payment.repository.command.TransactionCommand;
import com.payment.repository.command.WalletCommand;
import com.payment.repository.query.TransactionQuery;
import com.payment.repository.query.WalletQuery;
import com.payment.service.TransactionService;


@ApplicationScoped
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = getLogger(TransactionServiceImpl.class);
	
	@Inject
	TransactionQuery  transactionQuery;
	
	@Inject
	TransactionCommand transactionCommand;
	
	@Inject
	WalletQuery walletQuery;
	
	@Inject
	WalletCommand walletCommand;
	
	@Override
	public MoneyTransferModel transfer(MoneyTransfer trx) {
		return transferMoney(trx);
	}

	@Override
	public List<MoneyTransferModel> transactions() {
		return transactionQuery.findAllTransactions();
	}

	@Override
	public MoneyTransferModel getByTransactionId(String id) {
		return transactionQuery.getTransactionsByTransactionId(id);
	}

	@Override
	public List<MoneyTransferModel> getTransactionsBySender(String sender) {
		List<MoneyTransferModel> models = transactionQuery.getTransactionsBySender(sender);
		return models;
	}
	
	@Transactional
    public MoneyTransferModel transferMoney(MoneyTransfer transaction) {
        BigDecimal senderBalance = transactionQuery.getAccountBalance(transaction.getSender());
        if (transaction.getAmount().compareTo(senderBalance) > 0) {
            LOGGER.error("Insufficient balance to transfer money from {} to {}", transaction.getSender(),
                    transaction.getReceiver());

            throw new TransactionFailureException("Insufficient balance to transfer money. Wallet balance : " + senderBalance);
        }
        if (!walletQuery.doesWalletExist(transaction.getSender())) {
            LOGGER.error("Wallet not registered for mobile number :: {}", transaction.getSender());
            throw new NotFoundException("Wallet not registered for mobile number - " + transaction.getSender());
        }
        walletCommand.debitMoney(transaction);
        
        if (!walletQuery.doesWalletExist(transaction.getReceiver())) {
            LOGGER.error("Wallet not registered for mobile number :: {}", transaction.getReceiver());
            throw new NotFoundException("Wallet not registered for mobile number - " + transaction.getReceiver());
        }
        walletCommand.creditMoney(transaction);
        
        MoneyTransferModel txnModel = transactionCommand.addTransaction(transaction, Message.SUCCESS, TransactionCode.APPROVED);
        return txnModel;
    }
	
}
