package api.clients;

import api.models.CreateAccountRequest;
import api.models.VerifyLoginRequest;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigProvider;

import static io.restassured.RestAssured.given;

/**
 * AccountApiClient class to handle API requests for account-related operations such as creating an account,
 * verifying login, retrieving user details by email, handling product list requests, searching for products,
 * and deleting accounts.
 */
public class AccountApiClient {

	private static final Logger logger = LogManager.getLogger(AccountApiClient.class);

	// Constants for endpoints
	public static final String CREATE_ACCOUNT_ENDPOINT = "/api/createAccount";
	public static final String VERIFY_LOGIN_ENDPOINT = "/api/verifyLogin";
	public static final String GET_USER_DETAIL_BY_EMAIL_ENDPOINT = "/api/getUserDetailByEmail";
	public static final String PRODUCTS_LIST_ENDPOINT = "/api/productsList";
	public static final String INVALID_PRODUCTS_LIST_ENDPOINT = "/api/invalidProductsList";
	public static final String SEARCH_PRODUCT_ENDPOINT = "/api/searchProduct";
	public static final String DELETE_ACCOUNT_ENDPOINT = "/api/deleteAccount";

	// Constants for content types
	private static final String MULTIPART_FORM_DATA = "multipart/form-data";
	private static final String APPLICATION_JSON = "application/json";

	/**
	 * Sends a POST request to create an account.
	 * This method takes a CreateAccountRequest object and sends it as multipart form-data in the POST request
	 * to the /api/createAccount endpoint. Each field in the request is sent as a separate part.
	 */
	public Response createAccount(CreateAccountRequest request) {
		logger.info("Sending POST request to {} with multipart form data.", CREATE_ACCOUNT_ENDPOINT);

		Response response = given()
				.contentType(MULTIPART_FORM_DATA)
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
				.post(ConfigProvider.getBaseUri() + CREATE_ACCOUNT_ENDPOINT)
				.then()
				.extract()
				.response();

		logger.info("Received response: {}", response.asString());
		return response;
	}

	/**
	 * Sends a POST request to verify login.
	 * This method takes a VerifyLoginRequest object and sends it as multipart form-data in the POST request
	 * to the /api/verifyLogin endpoint. Each field (email and password) is sent as a separate part.
	 */
	public Response verifyLogin(VerifyLoginRequest request) {
		logger.info("Sending POST request to {} with multipart form data.", VERIFY_LOGIN_ENDPOINT);

		Response response = given()
				.contentType(MULTIPART_FORM_DATA)
				.multiPart("email", request.getEmail())
				.multiPart("password", request.getPassword())
				.post(ConfigProvider.getBaseUri() + VERIFY_LOGIN_ENDPOINT)
				.then()
				.extract()
				.response();

		logger.info("Received response: {}", response.asString());
		return response;
	}

	/**
	 * Sends a GET request to retrieve user details by email.
	 * This method sends a request to the /api/getUserDetailByEmail endpoint
	 * with the provided email as a query parameter.
	 */
	public Response getUserDetailByEmail(String email) {
		logger.info("Sending GET request to {} with email: {}", GET_USER_DETAIL_BY_EMAIL_ENDPOINT, email);

		Response response = given()
				.queryParam("email", email)
				.get(ConfigProvider.getBaseUri() + GET_USER_DETAIL_BY_EMAIL_ENDPOINT)
				.then()
				.extract()
				.response();

		logger.info("Received response: {}", response.asString());
		return response;
	}

	/**
	 * Sends a GET request to the /api/productsList endpoint.
	 * This method sends a request to the /api/productsList endpoint and logs the response.
	 */
	public Response getAllProducts() {
		logger.info("Sending GET request to {}", PRODUCTS_LIST_ENDPOINT);

		Response response = given()
				.get(ConfigProvider.getBaseUri() + PRODUCTS_LIST_ENDPOINT)
				.then()
				.extract()
				.response();

		logger.info("Received response: {}", response.asString());
		return response;
	}

	/**
	 * Sends a GET request to an invalid products list endpoint.
	 * This method is used to test the response from an invalid API endpoint: /api/invalidProductsList.
	 */
	public Response getInvalidProducts() {
		logger.info("Sending GET request to {}", INVALID_PRODUCTS_LIST_ENDPOINT);

		Response response = given()
				.get(ConfigProvider.getBaseUri() + INVALID_PRODUCTS_LIST_ENDPOINT)
				.then()
				.extract()
				.response();

		logger.info("Received response: {}", response.asString());
		return response;
	}

	/**
	 * Builds the RequestSpecification for searching a product with multipart/form-data content type.
	 * This method builds the request with the necessary parameters and content type.
	 */
	public RequestSpecification buildSearchProductRequest(String searchProduct) {
		logger.info("Building RequestSpecification for search product with {}", MULTIPART_FORM_DATA);

		return given()
				.contentType(MULTIPART_FORM_DATA)
				.multiPart("search_product", searchProduct);
	}

	/**
	 * Builds the RequestSpecification for searching a product with application/json content type.
	 * This is used for negative testing where we expect an error due to incorrect content type.
	 */
	public RequestSpecification buildSearchProductRequestWithJsonContentType(String searchProduct) {
		logger.info("Building RequestSpecification for search product with {}", APPLICATION_JSON);

		JsonObject jsonBody = new JsonObject();

		if (searchProduct != null && !searchProduct.isEmpty()) {
			jsonBody.addProperty("search_product", searchProduct);
		}

		return given()
				.contentType(APPLICATION_JSON)
				.body(jsonBody.toString());
	}

	/**
	 * Sends a POST request to search for a product.
	 * This method takes a RequestSpecification and sends the POST request to the /api/searchProduct endpoint.
	 */
	public Response sendSearchProductRequest(RequestSpecification requestSpec) {
		logger.info("Sending POST request to {}.", SEARCH_PRODUCT_ENDPOINT);

		Response response = requestSpec
				.post(ConfigProvider.getBaseUri() + SEARCH_PRODUCT_ENDPOINT)
				.then()
				.extract()
				.response();

		logger.info("Received response: {}", response.asString());
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
		logger.info("Sending DELETE request to {} with multipart form data.", DELETE_ACCOUNT_ENDPOINT);

		Response response = given()
				.contentType(MULTIPART_FORM_DATA)
				.multiPart("email", email)
				.multiPart("password", password)
				.delete(ConfigProvider.getBaseUri() + DELETE_ACCOUNT_ENDPOINT)
				.then()
				.extract()
				.response();

		logger.info("Received response: {}", response.asString());
		return response;
	}
}
