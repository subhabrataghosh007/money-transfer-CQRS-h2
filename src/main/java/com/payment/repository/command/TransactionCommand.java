package com.payment.repository.command;

import com.payment.constant.Message;
import com.payment.constant.TransactionCode;
import com.payment.dto.MoneyTransfer;
import com.payment.model.MoneyTransferModel;

public interface TransactionCommand {

	MoneyTransferModel addTransaction(MoneyTransfer transaction, Message message, TransactionCode code);

}
