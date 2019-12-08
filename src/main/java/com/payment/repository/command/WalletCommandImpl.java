package com.payment.repository.command;

import io.agroal.api.AgroalDataSource;
import org.slf4j.Logger;

import com.payment.common.DataSourceCreator;
import com.payment.dto.MoneyTransfer;
import com.payment.exception.InternalException;
import com.payment.exception.TransactionFailureException;
import com.payment.model.Wallet;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.payment.constant.SQLConstant.WALLET_CHECK_SQL;
import static com.payment.constant.SQLConstant.CREATE_WALLET_SQL;
import static com.payment.constant.SQLConstant.FIND_ALL_WALLET;
import static com.payment.constant.SQLConstant.CREDIT_MONEY_SQL;
import static com.payment.constant.SQLConstant.DEBIT_MONEY_SQL;
import static java.lang.Long.parseLong;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class WalletCommandImpl implements WalletCommand {
	
	private static final Logger LOGGER = getLogger(WalletCommandImpl.class);

//	@Inject
//	AgroalDataSource dataSource;

	@Inject
	DataSourceCreator dataSourceCreator;
	
	public void createWallet(Wallet wallet) {
		
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(CREATE_WALLET_SQL)) {

			LOGGER.info("Inserting Wallets data into Accounts table");
			preparedStatement.setString(1, wallet.getPhoneNumber());
			preparedStatement.setBigDecimal(2, wallet.getBalance());
			preparedStatement.execute();
			LOGGER.info("Wallet data successfully inserted into Wallets table");
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to insert data into Wallets table", ex);
			throw new InternalException("Error occurred to fetch data from Accounts table");
		}
	}

	public void addMoneyToWallet(Wallet wallet, String phoneNumber) {

		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(CREDIT_MONEY_SQL)) {

			LOGGER.info("Debiting money from account (Mobile number - {})", phoneNumber);
			preparedStatement.setBigDecimal(1, wallet.getBalance());
			preparedStatement.setString(2, phoneNumber);
			int updateCount = preparedStatement.executeUpdate();
			
			if (updateCount == 1) {
				LOGGER.info("Wallets balance updated for mobile number :: {}", phoneNumber);
			} else {
				LOGGER.error("Failed to update Wallets balance for mobile number :: {}", phoneNumber);
				throw new InternalException(
						"Failed to update Wallet balance for mobile number " + phoneNumber);
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to update balance in Wallets table", ex);
			throw new InternalException("Error occurred to update balance in Wallets table" + ex.getMessage());
		}

	}
	
	public void debitMoney(MoneyTransfer transaction) {
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(DEBIT_MONEY_SQL)) {

			LOGGER.info("Debiting money from wallet (Mobile number - {})", transaction.getSender());
			preparedStatement.setBigDecimal(1, transaction.getAmount());
			preparedStatement.setString(2, transaction.getSender());
			int updateCount = preparedStatement.executeUpdate();
			
			if (updateCount == 1) {
				LOGGER.info("Wallet balance updated for mobile number :: {}", transaction.getSender());
			} else {
				LOGGER.error("Failed to update wallet balance for mobile number :: {}", transaction.getSender());
				throw new TransactionFailureException(
						"Failed to update wallet balance for mobile number " + transaction.getSender());
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to update balance in wallet table", ex);
			throw new TransactionFailureException("Error occurred to update balance in wallet table");
		}
	}

	public void creditMoney(MoneyTransfer transaction) {
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(CREDIT_MONEY_SQL)) {

			LOGGER.info("Crediting money to wallet (Mobile number - {})", transaction.getReceiver());
			preparedStatement.setBigDecimal(1, transaction.getAmount());
			preparedStatement.setString(2, transaction.getReceiver());
			int updateCount = preparedStatement.executeUpdate();
			
			if (updateCount == 1) {
				LOGGER.info("Wallet balance updated for mobile number :: {}", transaction.getReceiver());
			} else {
				LOGGER.error("Failed to update wallet balance for mobile number :: {}", transaction.getReceiver());
				throw new TransactionFailureException(
						"Failed to update wallet balance for mobile number " + transaction.getReceiver());
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to update balance in Wallet table", ex);
			throw new TransactionFailureException("Error occurred to update balance - " + ex.getMessage());
		}
	}
}
