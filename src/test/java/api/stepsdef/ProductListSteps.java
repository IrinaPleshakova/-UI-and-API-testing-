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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Step definitions for Products List API with Allure reporting.
 */
public class ProductListSteps {

	private static final Logger logger = LogManager.getLogger(ProductListSteps.class);
	private final AccountApiClient client = new AccountApiClient();

	@Step("Ensure the product catalog contains multiple categories")
	@Given("the product catalog contains multiple categories")
	public void theProductCatalogContainsMultipleCategories() {
		Response response = client.getAllProducts();
		List<Map<String, Object>> products = response.jsonPath().getList("products");

		Set<String> categories = new HashSet<>();
		for (Map<String, Object> product : products) {
			categories.add(product.get("category").toString());
		}

		Assert.assertTrue(categories.size() > 1, "Catalog does not contain multiple categories.");
		logger.info("Catalog contains the following categories: {}", categories);
	}

	@Then("the response message should contain a non-empty list of products")
	public void theResponseMessageShouldContainANonEmptyListOfProducts() {
		List<?> products = CommonSteps.response.jsonPath().getList("products");

		if (products == null || products.isEmpty()) {
			logger.error("Product list is empty or missing in the response.");
			Assert.fail("Product list is empty or missing in the response.");
		}

		logger.info("Product list contains {} products.", products.size());
		Allure.addAttachment("Products Count", String.valueOf(products.size()));
	}

	@Then("each product should have mandatory fields: {string}, {string}, {string}, {string}")
	public void eachProductShouldHaveMandatoryFields(String id, String name, String price, String category) {
		List<Map<String, Object>> products = CommonSteps.response.jsonPath().getList("products");

		for (Map<String, Object> product : products) {
			Assert.assertNotNull(product.get(id), "Product is missing the 'id' field");
			Assert.assertNotNull(product.get(name), "Product is missing the 'name' field");
			Assert.assertNotNull(product.get(price), "Product is missing the 'price' field");
			Assert.assertNotNull(product.get(category), "Product is missing the 'category' field");

			logger.info("Product ID: {}, Name: {}, Price: {}, Category: {}",
					product.get(id), product.get(name), product.get(price), product.get(category));

			// Attach product details to Allure report
			Allure.addAttachment("Product Details", product.toString());
		}
	}

	@Then("all product prices should be greater than 0")
	public void allProductPricesShouldBeGreaterThan0() {
		List<Map<String, Object>> products = CommonSteps.response.jsonPath().getList("products");

		for (Map<String, Object> product : products) {
			double price = Double.parseDouble(product.get("price").toString().replaceAll("[^0-9.]", ""));
			Assert.assertTrue(price > 0, "Product has invalid price: " + price);
			logger.info("Product price is valid: {}", price);
		}
	}
}