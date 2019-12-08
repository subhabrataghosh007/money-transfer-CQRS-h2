package com.payment.repository.query;

import static com.payment.constant.SQLConstant.FETCH_BALANCE_SQL;
import static com.payment.constant.SQLConstant.FIND_ALL_TRANSACTION;
import static com.payment.constant.SQLConstant.FIND_ALL_TRANSACTION_BY_SENDER;
import static com.payment.constant.SQLConstant.FIND_ALL_TRANSACTION_BY_TRANSACTIONID;
import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.payment.common.DataSourceCreator;
import com.payment.exception.InternalException;
import com.payment.exception.NotFoundException;
import com.payment.model.MoneyTransferModel;

import io.agroal.api.AgroalDataSource;

@Singleton
public class TransactionQueryImpl implements TransactionQuery {
	private static final Logger LOGGER = getLogger(TransactionQueryImpl.class);

//	@Inject
//	AgroalDataSource dataSource;
	
	@Inject
	DataSourceCreator dataSourceCreator;

	public BigDecimal getAccountBalance(String mobileNumber) {

		//try (Connection conn = dataSource.getConnection();
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(FETCH_BALANCE_SQL)) {

			LOGGER.info("Fetching Wallet data from Wallet table for mobile number :: {}", mobileNumber);
			preparedStatement.setString(1, mobileNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					BigDecimal balance = resultSet.getBigDecimal(1);
					LOGGER.info("Wallet balance successfully fetched for mobile number :: {}", mobileNumber);
					return balance;
				} else {
					LOGGER.error("Wallet not registered for mobile number :: {}", mobileNumber);
					throw new NotFoundException("Wallet not registered for mobile number :: " + mobileNumber);

				}
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to fetch data from Wallet table", ex);
			throw new InternalException("Error occurred to fetch data from Wallet table");
		}
	}

	public List<MoneyTransferModel> findAllTransactions() {

		List<MoneyTransferModel> transferModels = new LinkedList<>();
		//try (Connection conn = dataSource.getConnection();
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_TRANSACTION)) {

			LOGGER.info("Retrieving all Transactions");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				transferModels = transactions(resultSet);
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to fetch data from Transactions table", ex);
			throw new InternalException("Error occurred to fetch data from Transactions table");
		}
		return transferModels;
	}

	public List<MoneyTransferModel> getTransactionsBySender(String sender) {

		List<MoneyTransferModel> transferModels = new LinkedList<>();
		//try (Connection conn = dataSource.getConnection();
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_TRANSACTION_BY_SENDER)) {
			preparedStatement.setString(1, sender);

			LOGGER.info("Retrieving all Transactions by " + sender);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				transferModels = transactions(resultSet);
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to fetch data from Transactions table", ex);
			throw new InternalException("Error occurred to fetch data from Transactions table");
		}
		return transferModels;
	}

	public MoneyTransferModel getTransactionsByTransactionId(String id) {
		MoneyTransferModel transferModel = null;

		//try (Connection conn = dataSource.getConnection();
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_TRANSACTION_BY_TRANSACTIONID)) {
			preparedStatement.setString(1, id);

			LOGGER.info("Retrieving all Transactions by " + id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					transferModel = getMoneyTransferModelFromRestlSet(resultSet);
				}
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to fetch data from Transactions table", ex);
			throw new InternalException("Error occurred to fetch data from Transactions table");
		}
		return transferModel;
	}

	
	private List<MoneyTransferModel> transactions(ResultSet resultSet) {
		List<MoneyTransferModel> transferModels = new LinkedList<>();

		try {
			while (resultSet.next()) {

				MoneyTransferModel model = getMoneyTransferModelFromRestlSet(resultSet);
				transferModels.add(model);
			}
		} catch (SQLException e) {
			LOGGER.error("Error occurred to fetch data from Transactions table", e);
			throw new InternalException("Error occurred to fetch data from Transactions table");
		}

		return transferModels;
	}

	private MoneyTransferModel getMoneyTransferModelFromRestlSet(ResultSet resultSet) {
		MoneyTransferModel model = null;

		try {
			model = new MoneyTransferModel.MoneyTransferModelBuilder()
					.setTransactionId(resultSet.getString("TransactionId"))
					.setAmount(new BigDecimal(resultSet.getString("Amount")))
					.setDescription(resultSet.getString("Description")).setReceiver(resultSet.getString("Receiver"))
					.setSender(resultSet.getString("Sender")).setStatus(resultSet.getString("Status"))
					.setStatusCode(resultSet.getString("StatusCode")).setTag(resultSet.getString("Tag"))
					.setTransferDateTime(resultSet.getString("TransferDateTime")).build();

		} catch (SQLException e) {
			throw new InternalException("Error occurred to fetch data from Transactions table");
		}

		return model;
	}

}
