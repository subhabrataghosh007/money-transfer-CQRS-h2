package com.payment.repository.command;

import static com.payment.constant.SQLConstant.CREATE_TRANSACTION_SQL;
import static com.payment.constant.SQLConstant.CREDIT_MONEY_SQL;
import static com.payment.constant.SQLConstant.DEBIT_MONEY_SQL;
import static com.payment.constant.SQLConstant.FETCH_BALANCE_SQL;
import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.payment.common.DataSourceCreator;
import com.payment.constant.Message;
import com.payment.constant.TransactionCode;
import com.payment.dto.MoneyTransfer;
import com.payment.exception.InternalException;
import com.payment.exception.NotFoundException;
import com.payment.exception.TransactionFailureException;
import com.payment.model.MoneyTransferModel;

import io.agroal.api.AgroalDataSource;

@Singleton
public class TransactionCommandImpl implements TransactionCommand {
	private static final Logger LOGGER = getLogger(TransactionCommandImpl.class);

//	@Inject
//	AgroalDataSource dataSource;
	
	@Inject
	DataSourceCreator dataSourceCreator;

	public MoneyTransferModel addTransaction(MoneyTransfer transaction, Message message, TransactionCode code) {

		MoneyTransferModel txnModel = createTransationModel(transaction, message, code);
		
		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(CREATE_TRANSACTION_SQL)) {

			LOGGER.info("Inserting Transaction data into Transactions table");
			preparedStatement.setString(1, txnModel.getTransactionId().toString());
			preparedStatement.setString(2, txnModel.getSender());
			preparedStatement.setString(3, txnModel.getReceiver());
			preparedStatement.setBigDecimal(4, txnModel.getAmount());
			preparedStatement.setString(5, txnModel.getTag());
			preparedStatement.setString(6, txnModel.getStatus());
			preparedStatement.setString(7, txnModel.getTransferDateTime());
			preparedStatement.setString(8, txnModel.getStatusCode());
			preparedStatement.setString(9, txnModel.getDescription());
			preparedStatement.execute();
			LOGGER.info("Wallet data successfully inserted into Account table");
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to insert data into Wallet table", ex);
			throw new InternalException("Error occurred to fetch data from Wallet table");
		}
		return txnModel;
	}
	
	private MoneyTransferModel createTransationModel(MoneyTransfer trx, Message message, TransactionCode code) {
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
