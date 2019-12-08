package com.payment.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.payment.constant.Message;
import com.payment.constant.TransactionCode;
import com.payment.dto.MoneyTransfer;
import com.payment.model.MoneyTransferModel;

public class Utility {

	public static boolean isValidAmount(String input) {
		try {
			new BigDecimal(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isEmpty(String str) {
		
		if (str == null) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		}  else {
			return false;
		}
	}
	
	public MoneyTransferModel createTransationModel(MoneyTransfer trx, Message message, TransactionCode code) {
		MoneyTransferModel txnModel = new MoneyTransferModel.MoneyTransferModelBuilder()
				.setSender(trx.getSender())
				.setReceiver(trx.getReceiver())
				.setAmount(trx.getAmount())
				.setTag(trx.getTag())
				.setStatus(message.getMessage())
				.setTransferDateTime(LocalDateTime.now().toString())
				.setDescription(code.getMessage())
				.setStatusCode(code.getCode())
				.build();
		return txnModel;
	}
}
