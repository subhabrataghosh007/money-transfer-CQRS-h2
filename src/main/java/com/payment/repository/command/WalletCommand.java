package com.payment.repository.command;

import com.payment.dto.MoneyTransfer;
import com.payment.model.Wallet;

public interface WalletCommand {

	void createWallet(Wallet wallet);

	void addMoneyToWallet(Wallet wallet, String phoneNumber);

	void debitMoney(MoneyTransfer transaction);

	void creditMoney(MoneyTransfer transaction);
}
