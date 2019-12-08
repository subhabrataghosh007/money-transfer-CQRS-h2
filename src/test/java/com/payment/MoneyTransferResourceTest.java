package com.payment;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;;

@QuarkusTest
public class MoneyTransferResourceTest {

	
	private static String CREATED = "{\"receiver\": \"9832799826\", \"sender\": \"9832799827\", \"amount\": 600, \"tag\": \"Transfering 600..\"}";
	private static String CREATED_SENDER_NON_NUMERIC = "{\"receiver\": \"983279982a\", \"sender\": \"9832799827\", \"amount\": 600, \"tag\": \"Transfering 600..\"}";
	private static String CREATED_RECEIVER_NON_NUMERIC = "{\"receiver\": \"9832799826\", \"sender\": \"983279982a\", \"amount\": 600, \"tag\": \"Transfering 600..\"}";
	private static String CREATED_RECEIVER_NOT_AVAILABLE = "{\"receiver\": \"9832799830\", \"sender\": \"9832799679\", \"amount\": 600, \"tag\": \"Transfering 600..\"}";
	private static String CREATED_INVALID_AMOUNT = "{\"receiver\": \"9832799830\", \"sender\": \"9832799831\", \"amount\": -2, \"tag\": \"Transfering -2..\"}";
	private static String CREATED_INSUFFICIENT_AMOUNT = "{\"receiver\": \"9832799826\", \"sender\": \"9832799827\", \"amount\": 10000, \"tag\": \"Transfering -2..\"}";
	
    @Test
    public void testTransactionNotAvailable() {
        given()
          .when().get("/v1/transactions")
          .then()
             .statusCode(Status.OK.getStatusCode());
    }
    
    @Test
    public void testTransactionByID() {
        given()
          .when().get("/v1/transactions/transaction/70d57d84-fdc2-418e-98ba-03bd1efd56dc/id")
          .then()
             .statusCode(Status.OK.getStatusCode())
             .body("sender", is("9832799826"))
             .body("receiver", is("9832799827"))
             .body("status", is("success"));
    }
    
    @Test
    public void testTransactionBySender() {
        given()
          .when().get("/v1/transactions/transaction/9832799826/sender")
          .then()
             .statusCode(Status.OK.getStatusCode())
             .body("[0].sender", is("9832799826"))
             .body("[0].receiver", is("9832799827"))
             .body("[0].status", is("success"));
    }
    
    @Test
    public void testTransactionCreated() {
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(CREATED)
          .post("/v1/transactions/transaction")
          .then()
             .statusCode(Status.CREATED.getStatusCode());
    }
    
    @Test
    public void testTransactionCreated_SENDER_NON_NUMERIC() {
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(CREATED_SENDER_NON_NUMERIC)
          .post("/v1/transactions/transaction")
          .then()
             .statusCode(Status.BAD_REQUEST.getStatusCode());
    }
    
    @Test
    public void testTransactionCreated_RECEIVER_NON_NUMERIC() {
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(CREATED_RECEIVER_NON_NUMERIC)
          .post("/v1/transactions/transaction")
          .then()
             .statusCode(Status.BAD_REQUEST.getStatusCode());
    }
    
    @Test
    public void testTransactionCreated_RECEIVER_NOT_AVAILABLE() {
    	given()
    	.when()
    	.accept(ContentType.JSON)
    	.contentType(ContentType.JSON)
    	.body(CREATED_RECEIVER_NOT_AVAILABLE)
    	.post("/v1/transactions/transaction")
    	.then()
    	.statusCode(Status.NOT_FOUND.getStatusCode())
    	.body("errorMessage", is("Wallet not registered for mobile number :: 9832799679"));
    	
    }
    
    @Test
    public void testTransaction_CREATED_INVALID_AMOUNT() {
    	given()
    	.when()
    	.accept(ContentType.JSON)
    	.contentType(ContentType.JSON)
    	.body(CREATED_INVALID_AMOUNT)
    	.post("/v1/transactions/transaction")
    	.then()
    	.statusCode(Status.BAD_REQUEST.getStatusCode())
    	.body("errorMessage", is("Amount Should be Positive"));
    	
    }
    
    @Test
    public void testTransaction_CREATED_INSUFFICIENT_AMOUNT() {
    	given()
    	.when()
    	.accept(ContentType.JSON)
    	.contentType(ContentType.JSON)
    	.body(CREATED_INSUFFICIENT_AMOUNT)
    	.post("/v1/transactions/transaction")
    	.then()
    	.statusCode(Status.EXPECTATION_FAILED.getStatusCode())
    	.body("errorMessage", is("Insufficient balance to transfer money. Wallet balance : 3000"));
    	
    	
    }
    
}