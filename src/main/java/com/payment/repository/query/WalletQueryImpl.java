package com.payment.repository.query;

import static com.payment.constant.SQLConstant.FIND_ALL_WALLET;
import static com.payment.constant.SQLConstant.WALLET_CHECK_SQL;
import static java.lang.Long.parseLong;
import static org.slf4j.LoggerFactory.getLogger;

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
import com.payment.model.Wallet;

import io.agroal.api.AgroalDataSource;

@Singleton
public class WalletQueryImpl implements WalletQuery {

	private static final Logger LOGGER = getLogger(WalletQueryImpl.class);

//	@Inject
//	AgroalDataSource dataSource;
	
	@Inject
	DataSourceCreator dataSourceCreator;

	public boolean doesWalletExist(String mobileNumber) {

		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(WALLET_CHECK_SQL)) {

			boolean isAccountPresent = false;
			LOGGER.info("Checking if Wallet with mobile number {} exists", mobileNumber);
			preparedStatement.setLong(1, parseLong(mobileNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next() && resultSet.getInt(1) != 0)
					isAccountPresent = true;
			}
			return isAccountPresent;
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to fetch data from Wallets table", ex);
			throw new InternalException("Error occurred to fetch data from Wallets table");
		}
	}

	public List<Wallet> findAllWallets() {

		List<Wallet> wallets = new LinkedList<>();

		try (Connection conn = dataSourceCreator.getAgroalDataSourceInstance().getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_WALLET)) {

			LOGGER.info("Retrieving all wallets");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					Wallet wallet = new Wallet();
					wallet.setPhoneNumber(resultSet.getObject("PHONE_NUMBER").toString());
					wallet.setBalance(resultSet.getBigDecimal("BALANCE"));

					wallets.add(wallet);
				}
			}
		} catch (SQLException ex) {
			LOGGER.error("Error occurred to fetch data from Wallets table", ex);
			throw new InternalException("Error occurred to fetch data from Wallets table");
		}

		return wallets;
	}

}
