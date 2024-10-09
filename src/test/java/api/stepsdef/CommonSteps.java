package api.stepsdef;

import api.clients.AccountApiClient;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

/**
 * Common step definitions for handling API requests and responses with Allure reporting.
 */
public class CommonSteps {

	private static final Logger logger = LogManager.getLogger(CommonSteps.class);
	public static Response response;
	private final AccountApiClient client = new AccountApiClient();

	@Step("Sending {method} request to endpoint {endpoint}")
	private void sendRequest(String method, String endpoint) {
		logger.info("Sending {} request to endpoint: {}", method, endpoint);

		switch (endpoint) {
			case "/api/createAccount":
				logger.info("Creating account with request: {}", AccountSteps.createAccountRequest);
				Allure.addAttachment("Account Creation Request", AccountSteps.createAccountRequest.toString());
				response = client.createAccount(AccountSteps.createAccountRequest);
				break;
			case "/api/verifyLogin":
				logger.info("Verifying login with request: {}", LoginSteps.verifyLoginRequest);
				Allure.addAttachment("Login Verification Request", LoginSteps.verifyLoginRequest.toString());
				response = client.verifyLogin(LoginSteps.verifyLoginRequest);
				break;
			case "/api/searchProduct":
				logger.info("Searching product with name: {}", SearchProductsSteps.searchProduct);
				Allure.addAttachment("Product Search Request", SearchProductsSteps.searchProduct);
				response = client.searchProduct(SearchProductsSteps.searchProduct, true);
				break;
			case "/api/getUserDetailByEmail":
				logger.info("Getting user details for email: {}", UserDetailByEmailSteps.email);
				Allure.addAttachment("User Detail Request", UserDetailByEmailSteps.email);
				response = client.getUserDetailByEmail(UserDetailByEmailSteps.email);
				break;
			case "/api/productsList":
				logger.info("Fetching all products.");
				response = client.getAllProducts();
				break;
			case "/api/invalidProductsList":
				logger.info("Fetching invalid products.");
				response = client.getInvalidProducts();
				break;
			case "/api/deleteAccount":
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

	@When("I send a POST request to {string} endpoint")
	public void iSendAPostRequestToEndpoint(String endpoint) {
		if ("/api/searchProduct".equals(endpoint)) {
			handleSearchProductRequest();  // Call of special logic for searchProduct
		} else {
			sendRequest("POST", endpoint);  // Common logic for all other POST requests
		}
	}

	// Special logic for searchProduct
	private void handleSearchProductRequest() {
		logger.info("Searching product with name: {}", SearchProductsSteps.searchProduct);

		// If needed for a negative test, pass JSON
		boolean useJson = SearchProductsSteps.searchProduct == null;
		response = new AccountApiClient().searchProduct(SearchProductsSteps.searchProduct, useJson);

		logger.info("Response received for search product: {}", response.asString());
		addResponseToAllure(response);
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