package api.stepsdef;

import api.clients.AccountApiClient;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

/**
 * Common step definitions for sending requests and handling responses.
 */
public class CommonSteps {

	private static final Logger logger = LogManager.getLogger(CommonSteps.class);
	private final AccountApiClient client = new AccountApiClient();
	public static Response response;

	@Step("Sending {method} request to endpoint {endpoint}")
	@When("I send a {string} request to {string} endpoint")
	public void sendRequest(String method, String endpoint) {
		logger.info("Sending {} request to endpoint: {}", method, endpoint);

		switch (endpoint) {
			case AccountApiClient.CREATE_ACCOUNT_ENDPOINT:
				logger.info("Creating account with request: {}", AccountSteps.createAccountRequest);
				Allure.addAttachment("Account Creation Request", AccountSteps.createAccountRequest.toString());
				response = client.createAccount(AccountSteps.createAccountRequest);
				break;
			case AccountApiClient.VERIFY_LOGIN_ENDPOINT:
				logger.info("Verifying login with request: {}", VerifyLoginSteps.verifyLoginRequest);
				Allure.addAttachment("Login Verification Request", VerifyLoginSteps.verifyLoginRequest.toString());
				response = client.verifyLogin(VerifyLoginSteps.verifyLoginRequest);
				break;
			case AccountApiClient.SEARCH_PRODUCT_ENDPOINT:
				logger.info("Preparing to search product.");
				handleSearchProductRequest(method);
				break;
			case AccountApiClient.GET_USER_DETAIL_BY_EMAIL_ENDPOINT:
				logger.info("Getting user details for email: {}", UserDetailByEmailSteps.email);
				Allure.addAttachment("User Detail Request", UserDetailByEmailSteps.email);
				response = client.getUserDetailByEmail(UserDetailByEmailSteps.email);
				break;
			case AccountApiClient.PRODUCTS_LIST_ENDPOINT:
				logger.info("Fetching all products.");
				response = client.getAllProducts();
				break;
			case AccountApiClient.INVALID_PRODUCTS_LIST_ENDPOINT:
				logger.info("Fetching invalid products.");
				response = client.getInvalidProducts();
				break;
			case AccountApiClient.DELETE_ACCOUNT_ENDPOINT:
				logger.info("Deleting account with email: {}", DeleteAccountSteps.email);
				Allure.addAttachment("Account Deletion Request", "Email: " + DeleteAccountSteps.email);
				response = client.deleteAccount(DeleteAccountSteps.email, DeleteAccountSteps.password);
				break;
			default:
				logger.error("Unknown endpoint: {}", endpoint);
				throw new IllegalArgumentException("Unknown endpoint: " + endpoint);
		}
		logger.info("Response received: {}", response.asString());
		addResponseToAllure(response);
	}

	// Method to handle search product request
	private void handleSearchProductRequest(String method) {
		RequestSpecification requestSpec;
		if ("POST".equalsIgnoreCase(method)) {
			if (SearchProductsSteps.searchProduct != null) {
				// Build the request with correct content type
				requestSpec = client.buildSearchProductRequest(SearchProductsSteps.searchProduct);
			} else {
				// Build the request with incorrect content type for negative testing
				requestSpec = client.buildSearchProductRequestWithJsonContentType(SearchProductsSteps.searchProduct);
			}
			// Send the request
			response = client.sendSearchProductRequest(requestSpec);
		} else {
			logger.error("Unsupported method for search product: {}", method);
			throw new IllegalArgumentException("Unsupported method for search product: " + method);
		}
	}

	@When("I send a POST request to {string} endpoint")
	public void iSendAPostRequestToEndpoint(String endpoint) {
		sendRequest("POST", endpoint);
	}

	@When("I send a GET request to {string} endpoint")
	public void iSendAGetRequestToEndpoint(String endpoint) {
		sendRequest("GET", endpoint);
	}

	@When("I send a DELETE request to {string} endpoint")
	public void iSendADeleteRequestToEndpoint(String endpoint) {
		sendRequest("DELETE", endpoint);
	}

	@Step("Asserting response code should be {expectedResponseCode}")
	@Then("I should receive a response code {int}")
	public void iShouldReceiveAResponseCode(Integer expectedResponseCode) {
		int actualResponseCode = response.jsonPath().getInt("responseCode");
		logger.info("Asserting response code: Expected = {}, Actual = {}", expectedResponseCode, actualResponseCode);
		Allure.addAttachment("Expected Response Code", String.valueOf(expectedResponseCode));
		Allure.addAttachment("Actual Response Code", String.valueOf(actualResponseCode));
		Assert.assertEquals(actualResponseCode, expectedResponseCode.intValue(), "Response code mismatch");
	}

	@Step("Asserting status code should be {expectedStatusCode}")
	@Then("I should receive a status code {int}")
	public void iShouldReceiveAStatusCode(int expectedStatusCode) {
		int actualStatusCode = response.getStatusCode();
		logger.info("Asserting HTTP status code: Expected = {}, Actual = {}", expectedStatusCode, actualStatusCode);
		Allure.addAttachment("Expected Status Code", String.valueOf(expectedStatusCode));
		Allure.addAttachment("Actual Status Code", String.valueOf(actualStatusCode));
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "HTTP status code mismatch");
	}

	@Step("Asserting response message should be {expectedMessage}")
	@Then("the response message should be {string}")
	public void theResponseMessageShouldBe(String expectedMessage) {
		String actualMessage = response.jsonPath().getString("message");
		logger.info("Asserting response message: Expected = {}, Actual = {}", expectedMessage, actualMessage);
		Allure.addAttachment("Expected Response Message", expectedMessage);
		Allure.addAttachment("Actual Response Message", actualMessage);
		Assert.assertEquals(actualMessage, expectedMessage, "Response message mismatch");
	}

	@Attachment(value = "Response Body", type = "application/json")
	private String addResponseToAllure(Response response) {
		return response.asString();
	}
}
