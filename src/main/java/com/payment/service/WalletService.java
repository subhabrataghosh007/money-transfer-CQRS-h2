package com.payment.service;

import java.util.List;

import com.payment.model.Wallet;

public interface WalletService {

	public List<Wallet> allWallets();

	public void addWallet(Wallet wallet);

	public void addMoney(Wallet wallet, String phoneNumber);

}
