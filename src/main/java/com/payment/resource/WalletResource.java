package com.payment.resource;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

import com.payment.common.Utility;
import com.payment.dto.APIResponse;
import com.payment.exception.BadRequestException;
import com.payment.model.Wallet;
import com.payment.service.WalletService;
import com.payment.validation.CustomeValidation;


@Path("/v1/wallets")
public class WalletResource {

	private static final Logger LOGGER = getLogger(WalletResource.class);
	
	@Inject
	WalletService walletService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAccounts() {
		
		List<Wallet> wallets = walletService.allWallets();
		return Response.ok(wallets).build();
	}

	@POST
	@Path("/wallet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(Wallet wallet) {
		
		LOGGER.info("Input request :: {}", wallet);
		CustomeValidation.validateWallet(wallet);
	    
		walletService.addWallet(wallet);
		
		APIResponse response = new APIResponse(Status.CREATED, "Account registered successfully");
		return Response.status(Status.CREATED).entity(response).build();
	}

	@PUT
	@Path("/wallet/addmoney/{phoneNumber}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMoney(Wallet wallet, @PathParam(value = "phoneNumber") String phoneNumber) {

		if (Utility.isEmpty(phoneNumber)) {
			throw new BadRequestException("Please provide phone number");
		}
		
		CustomeValidation.validateWallet(wallet);
		
		LOGGER.info("Input request :: {}", wallet);
		
		walletService.addMoney(wallet, phoneNumber);
		APIResponse response = new APIResponse(Status.OK, String.format("Amount %s added to the Account %s", wallet.getBalance(), phoneNumber));
		return Response.status(Status.OK).entity(response).build();

	}
	
}