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
	 * This method takes an object and sends it as multipart form-data in the POST request
	 * to the /api/createAccount endpoint. Each field in the request is sent as a separate part.
	 */

	public Response createAccount(CreateAccountRequest request) {
		logger.info("Sending POST request to /api/createAccount with multipart form data.");

		Response response = given()
				.contentType("multipart/form-data")
				.multiPart("name", request.getName())
				.multiPart("email", request.getEmail())
				.multiPart("password", request.getPassword())
				.multiPart("title", request.getTitle())
				.multiPart("birth_month", request.getBirth_month())
				.multiPart("birth_date", request.getBirth_date())
				.multiPart("birth_year", request.getBirth_year())
				.multiPart("firstname", request.getFirstname())
				.multiPart("lastname", request.getLastname())
				.multiPart("company", request.getCompany())
				.multiPart("address1", request.getAddress1())
				.multiPart("address2", request.getAddress2())
				.multiPart("country", request.getCountry())
				.multiPart("zipcode", request.getZipcode())
				.multiPart("state", request.getState())
				.multiPart("city", request.getCity())
				.multiPart("mobile_number", request.getMobile_number())
				.post(ConfigProvider.getBaseUri() + "/api/createAccount")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}

	/**
	 * Sends a POST request to verify login.
	 * This method takes an object and sends it as multipart form-data in the POST request
	 * to the /api/verifyLogin endpoint. Each field (email and password) is sent as a separate part.
	 */
	public Response verifyLogin(VerifyLoginRequest request) {
		logger.info("Sending POST request to /api/verifyLogin with multipart form data.");

		Response response = given()
				.contentType("multipart/form-data")
				.multiPart("email", request.getEmail())
				.multiPart("password", request.getPassword())
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
	 * This method sends a request to the /api/searchProduct endpoint with a search query as multipart form-data.
	 * The search query is sent as a form field using the multipart format.
	 */
	public Response searchProduct(String searchProduct) {
		logger.info("Sending POST request to /api/searchProduct with multipart form data.");

		Response response = given()
				.contentType("multipart/form-data")
				.multiPart("search_product", searchProduct)
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
	 * with the provided email and password as form data (multipart).
	 * Each field (email and password) is sent as a separate part in the request.
	 * The request is made with the content type set to "multipart/form-data".
	 */
	public Response deleteAccount(String email, String password) {
		logger.info("Sending DELETE request to /api/deleteAccount with multipart form data.");

		Response response = given()
				.contentType("multipart/form-data")
				.multiPart("email", email)
				.multiPart("password", password)
				.delete(ConfigProvider.getBaseUri() + "/api/deleteAccount")
				.then()
				.extract()
				.response();

		logger.info("Received response: " + response.asString());
		return response;
	}
}


