package com.payment.common;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.agroal.api.AgroalDataSource;

@Singleton
public class DataSourceCreator {

	@Inject
	AgroalDataSource dataSource;
	
	private DataSourceCreator() {}
	
	public AgroalDataSource getAgroalDataSourceInstance() {
		return dataSource;
	}
}
