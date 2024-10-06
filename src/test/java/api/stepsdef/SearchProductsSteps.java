package api.stepsdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

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
	}

	@Step("No product name provided for search")
	@Given("I do not provide a product name for search")
	public void iDoNotProvideAProductNameForSearch() {
		logger.info("No product name provided for search.");
		searchProduct = null;  // Indicating no search term provided
	}
	@Then("the products list should contain searched products")
	public void theProductsListShouldContainSearchedProducts() {
		if (CommonSteps.response.jsonPath().getList("products").isEmpty()) {
			logger.error("Products list is empty or no products match the search.");
			Assert.fail("Products list is empty or no products match the search.");
		}
		logger.info("Products list contains searched products.");
	}

}
