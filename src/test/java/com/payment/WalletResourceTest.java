package com.payment;

import static com.payment.constant.ValidationMessage.AMOUNT_ERROR;
import static com.payment.constant.ValidationMessage.MOBILE_VALIDATION;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;;

@QuarkusTest
public class WalletResourceTest {

	private static String BASE_URL = "/v1/wallets";
	private static String BODY_HAPPY_PATH = "{\"phoneNumber\": \"9832799828\",\"balance\": 1000}";
	private static String BODY_BAD_REQUEST_PHONE_NON_NUMERIC = "{\"phoneNumber\": \"9832799828SS\",\"balance\": 1000}";
	private static String BODY_BAD_REQUEST_PHONE_LESS_10 = "{\"phoneNumber\": \"983279982\",\"balance\": 1000}";
	private static String BODY_BAD_REQUEST_PHONE_GREATER_10 = "{\"phoneNumber\": \"983279982233\",\"balance\": 1000}";
	private static String BODY_BAD_REQUEST_BALANCE_ZERO = "{\"phoneNumber\": \"9832799829\",\"balance\": 0}";
	private static String BODY_BAD_REQUEST_BALANCE_NEGATIVE = "{\"phoneNumber\": \"9832799829\",\"balance\": -1}";

	
    @Test
    public void testWalletAccountService() {
        given()
          .when().get(BASE_URL)
          .then()
             .statusCode(200);
    }
    
    @Test
    public void testAddWallet() {
    	
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(BODY_HAPPY_PATH)
          .post(BASE_URL+"/wallet")
          .then()
             .statusCode(201);
    }
    
    @Test
    public void testAddWallet_BAD_REQUEST_PHONE_NON_NUMERIC() {
    	
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(BODY_BAD_REQUEST_PHONE_NON_NUMERIC)
          .post(BASE_URL+"/wallet")
          .then()
             .statusCode(400)
             .body("errorMessage", containsString(MOBILE_VALIDATION));
    }
    
    @Test
    public void testAddWallet_BAD_REQUEST_PHONE_LESS_10() {
    	
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(BODY_BAD_REQUEST_PHONE_LESS_10)
          .post(BASE_URL+"/wallet")
          .then()
             .statusCode(400)
             .body("errorMessage", containsString(MOBILE_VALIDATION));
    }
    
    @Test
    public void testAddWallet_BAD_REQUEST_PHONE_GREATER_10() {
    	
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(BODY_BAD_REQUEST_PHONE_GREATER_10)
          .post(BASE_URL+"/wallet")
          .then()
             .statusCode(400)
             .body("errorMessage", containsString(MOBILE_VALIDATION));
    }
    
    @Test
    public void testAddWallet_BAD_REQUEST_BALANCE_ZERO() {
    	
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(BODY_BAD_REQUEST_BALANCE_ZERO)
          .post(BASE_URL+"/wallet")
          .then()
             .statusCode(400)
             .body("errorMessage", containsString(AMOUNT_ERROR));
    }
    
    @Test
    public void testAddWallet_BAD_REQUEST_BALANCE_NEGATIVE() {
    	
        given()
          .when()
          .accept(ContentType.JSON)
          .contentType(ContentType.JSON)
          .body(BODY_BAD_REQUEST_BALANCE_NEGATIVE)
          .post(BASE_URL+"/wallet")
          .then()
             .statusCode(400)
             .body("errorMessage", containsString(AMOUNT_ERROR));
    }
    
}