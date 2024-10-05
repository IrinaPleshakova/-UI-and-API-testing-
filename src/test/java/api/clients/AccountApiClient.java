package api.clients;

import api.models.CreateAccountRequest;
import api.models.VerifyLoginRequest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigProvider;
import com.google.gson.Gson;

import static io.restassured.RestAssured.given;

/**
 * AccountApiClient class to handle API requests for account-related operations such as creating an account,
 * verifying login, retrieving user details by email, handling product list requests, searching for products,
 * and deleting accounts.
 */
public class AccountApiClient {

	private static final Logger logger = LogManager.getLogger(AccountApiClient.class);

	/**
	 * Sends a POST request to create an account.
	 * This method takes an object and sends it as a JSON body in the POST request
	 * to the /api/createAccount endpoint.
	 */
	public Response createAccount(CreateAccountRequest request) {
		// Use Gson to convert the request object into a JSON string
		Gson gson = new Gson();
		String jsonBody = gson.toJson(request);  // Serialize the object into a JSON string

		logger.info("Sending POST request to /api/createAccount with JSON: " + jsonBody);

		Response response = given()
				.contentType("application/json")
				.body(jsonBody)  // Send the JSON string as the request body
				.post(ConfigProvider.getBaseUri() + "/api/createAccount")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}

	/**
	 * Sends a POST request to verify login.
	 * This method takes an object and sends it as a JSON body in the POST request
	 * to the /api/verifyLogin endpoint.
	 */
	public Response verifyLogin(VerifyLoginRequest request) {
		Gson gson = new Gson();
		String jsonBody = gson.toJson(request);

		logger.info("Sending POST request to /api/verifyLogin with JSON: " + jsonBody);

		Response response = given()
				.contentType("application/json")
				.body(jsonBody)  // Send the JSON string as the request body
				.post(ConfigProvider.getBaseUri() + "/api/verifyLogin")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}

	/**
	 * Sends a GET request to retrieve user details by email.
	 * This method sends a request to the /api/getUserDetailByEmail endpoint
	 * with the provided email as a query parameter.
	 */
	public Response getUserDetailByEmail(String email) {
		logger.info("Sending GET request to /api/getUserDetailByEmail with email: " + email);

		Response response = given()
				.queryParam("email", email)
				.get(ConfigProvider.getBaseUri() + "/api/getUserDetailByEmail")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}

	/**
	 * Sends a GET request to the /api/productsList endpoint.
	 * This method sends a request to the /api/productsList endpoint and logs the response.
	 */
	public Response getAllProducts() {
		logger.info("Sending GET request to /api/productsList");

		Response response = given()
				.get(ConfigProvider.getBaseUri() + "/api/productsList")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}

	/**
	 * Sends a GET request to an invalid products list endpoint.
	 * This method is used to test the response from an invalid API endpoint: /api/invalidProductsList.
	 */
	public Response getInvalidProducts() {
		logger.info("Sending GET request to /api/invalidProductsList");

		Response response = given()
				.get(ConfigProvider.getBaseUri() + "/api/invalidProductsList")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}

	/**
	 * Sends a POST request to search for a product.
	 * This method sends a request to the /api/searchProduct endpoint with a search query as a parameter.
	 */
	public Response searchProduct(String searchProduct) {
		logger.info("Sending POST request to /api/searchProduct with search_product: " + searchProduct);

		Response response = given()
				.contentType("application/json")
				.queryParam("search_product", searchProduct)
				.post(ConfigProvider.getBaseUri() + "/api/searchProduct")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}

	/**
	 * Sends a DELETE request to delete an account.
	 * This method sends a request to the /api/deleteAccount endpoint
	 * with the provided email and password as query parameters.
	 */
	public Response deleteAccount(String email, String password) {
		logger.info("Sending DELETE request to /api/deleteAccount with email: " + email);

		Response response = given()
				.queryParam("email", email)
				.queryParam("password", password)
				.delete(ConfigProvider.getBaseUri() + "/api/deleteAccount")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}
}


