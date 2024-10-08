package api.stepsdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;

/**
 * Step definition class for product search API with Allure reporting.
 */
public class SearchProductsSteps {

	private static final Logger logger = LogManager.getLogger(SearchProductsSteps.class);
	public static String searchProduct;

	@Step("Setting valid product name for search")
	@Given("I have a valid product name to search")
	public void iHaveAValidProductNameToSearch() {
		logger.info("Setting valid product name for search.");
		searchProduct = "top";
		Allure.addAttachment("Search Product Name", searchProduct);
	}

	@Step("No product name provided for search")
	@Given("I do not provide a product name for search")
	public void iDoNotProvideAProductNameForSearch() {
		logger.info("No product name provided for search.");
		searchProduct = null;  // Indicating no search term provided
		Allure.addAttachment("Search Product Name", "No product name provided");
	}

	@Then("the products list should contain searched products")
	public void theProductsListShouldContainSearchedProducts() {
		List<?> products = CommonSteps.response.jsonPath().getList("products");

		if (products.isEmpty()) {
			logger.error("Products list is empty or no products match the search.");
			Allure.addAttachment("Error", "Products list is empty or no products match the search.");
			Assert.fail("Products list is empty or no products match the search.");
		}

		logger.info("Products list contains {} products.", products.size());

		// Convert the product list into a string to add to the Allure report
		StringBuilder productsListString = new StringBuilder();
		for (int i = 0; i < products.size(); i++) {
			productsListString.append("Product ").append(i + 1).append(": ")
					.append(products.get(i).toString()).append("\n");
		}

		// Adding a list of products to the Allure report
		Allure.addAttachment("Found Products List", productsListString.toString());
		logger.info("Products list contains searched products.");
	}
}
