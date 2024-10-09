package api.stepsdef;

import api.clients.AccountApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;

import static api.stepsdef.CommonSteps.response;

/**
 * Step definition class for product search API with Allure reporting.
 */
public class SearchProductsSteps {

	private static final Logger logger = LogManager.getLogger(SearchProductsSteps.class);
	public static String searchProduct;
	private final AccountApiClient client = new AccountApiClient();

	/**
	 * Helper method to perform the product search and store the response.
	 */
	private void performSearch(String productName, boolean useJson) {
		logger.info("Performing search for product: {}", productName != null ? productName : "No product name provided");

		// Send request using either form-data or JSON
		Response response = client.searchProduct(productName, useJson);
		CommonSteps.response = response;  // Store response for later validation

		// Log and attach product name or lack thereof
		String attachmentName = productName != null ? productName : "No product name provided";
		Allure.addAttachment("Search Product Name", attachmentName);
	}

	@Step("Setting valid product name for search")
	@Given("I have a valid product name to search")
	public void iHaveAValidProductNameToSearch() {
		searchProduct = "top";
		performSearch(searchProduct, false);  // Send multipart/form-data request
	}

	@Step("No product name provided for search")
	@Given("I do not provide a product name for search")
	public void iDoNotProvideAProductNameForSearch() {
		searchProduct = null;  // No product name provided
		performSearch(searchProduct, true);  // Send JSON request to trigger an error
	}

	@Then("the products list should contain searched products")
	public void theProductsListShouldContainSearchedProducts() {
		List<?> products = response.jsonPath().getList("products");

		if (products.isEmpty()) {
			String errorMessage = "Products list is empty or no products match the search.";
			logger.error(errorMessage);
			Allure.addAttachment("Error", errorMessage);
			Assert.fail(errorMessage);
		}

		logger.info("Products list contains {} products.", products.size());

		// Convert products list to string and attach it to Allure report
		StringBuilder productsListString = new StringBuilder();
		for (int i = 0; i < products.size(); i++) {
			productsListString.append("Product ").append(i + 1).append(": ")
					.append(products.get(i).toString()).append("\n");
		}

		Allure.addAttachment("Found Products List", productsListString.toString());
		logger.info("Products list contains searched products.");
	}
}
