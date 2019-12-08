package com.payment.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.payment.dto.MoneyTransfer;
import com.payment.model.MoneyTransferModel;
import com.payment.service.TransactionService;
import com.payment.validation.CustomeValidation;


@Path("/v1/transactions")
public class TransactionResource {

	@Inject
	TransactionService transactionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTransactions() {
		
		List<MoneyTransferModel> transactions = transactionService.transactions();
		return Response.ok(transactions).build();
	}
	
	@GET
	@Path("/transaction/{id}/id")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionById(@Valid @NotNull @NotEmpty @PathParam(value = "id") String id) {
		
		MoneyTransferModel transaction = transactionService.getByTransactionId(id);
		return Response.ok(transaction).build();
	}
	
	@GET
	@Path("/transaction/{sender}/sender")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionBySender(@PathParam(value = "sender") String sender) {
		
		List<MoneyTransferModel> transactions = transactionService.getTransactionsBySender(sender);
		return Response.ok(transactions).build();
	}
	
	@POST
	@Path("/transaction")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeTransaction(MoneyTransfer txn) {

		CustomeValidation.validateWallet(txn);
		
		MoneyTransferModel model = transactionService.transfer(txn);
		
		return Response.status(Status.CREATED).entity(model).build();
	}
}
