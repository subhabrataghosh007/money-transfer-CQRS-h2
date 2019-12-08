package com.payment.repository.query;

import java.util.List;

import com.payment.model.Wallet;

public interface WalletQuery {
	
	boolean doesWalletExist(String mobileNumber);
	
	List<Wallet> findAllWallets();

}
