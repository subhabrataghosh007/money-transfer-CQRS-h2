package com.payment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class MoneyTransferModel {

	private UUID transactionId;
	private String sender;
	private String receiver;
	private BigDecimal amount;
	private String tag;
	private String status;
	private String transferDateTime;
	private String statusCode;
	private String description;

	private MoneyTransferModel(MoneyTransferModelBuilder builder) {
		this.transactionId = builder.transactionId;
		this.sender = builder.sender;
		this.receiver = builder.receiver;
		this.amount = builder.amount;
		this.tag = builder.tag;
		this.status = builder.status;
		this.transferDateTime = builder.transferDateTime;
		this.statusCode = builder.statusCode;
		this.description = builder.description;
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getStatus() {
		return status;
	}

	public String getTransferDateTime() {
		return transferDateTime;
	}

	public String getTag() {
		return tag;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getDescription() {
		return description;
	}

	public static class MoneyTransferModelBuilder {
		private UUID transactionId;
		private String sender;
		private String receiver;
		private BigDecimal amount;
		private String tag;
		private String status;
		private String transferDateTime;
		private String statusCode;
		private String description;

		public MoneyTransferModelBuilder() {
			this.transactionId = UUID.randomUUID();
		}

		public MoneyTransferModelBuilder setTransactionId(String transactionId) {
			this.transactionId = UUID.fromString(transactionId);
			return this;
		}
		
		public MoneyTransferModelBuilder setAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public MoneyTransferModelBuilder setStatus(String status) {
			this.status = status;
			return this;
		}

		public MoneyTransferModelBuilder setTransferDateTime(String transferDateTime) {
			this.transferDateTime = transferDateTime;
			return this;
		}

		public MoneyTransferModelBuilder setSender(String sender) {
			this.sender = sender;
			return this;
		}

		public MoneyTransferModelBuilder setReceiver(String receiver) {
			this.receiver = receiver;
			return this;
		}

		public MoneyTransferModelBuilder setTag(String tag) {
			this.tag = tag;
			return this;
		}
		
		public MoneyTransferModelBuilder setStatusCode(String statusCode) {
			this.statusCode = statusCode;
			return this;
		}

		public MoneyTransferModelBuilder setDescription(String description) {
			this.description = description;
			return this;
		}

		public MoneyTransferModel build() {
			return new MoneyTransferModel(this);
		}
	}
}