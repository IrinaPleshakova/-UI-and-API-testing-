package api.stepsdef;

import com.google.gson.*;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;

/**
 * Step definitions for Products List API with Allure reporting.
 */
public class ProductListSteps {

	private static final Logger logger = LogManager.getLogger(ProductListSteps.class);

	@Step("Asserting that the response contains a list of products")
	@Then("the response message should contain {string}")
	public void theResponseMessageShouldContain(String expectedMessage) {
		List<?> products = CommonSteps.response.jsonPath().getList("products"); // Используем List<?>
		if (products.isEmpty()) {
			logger.error("Products list is empty or not present in the response.");
			Assert.fail("Products list is empty or not present in the response.");
		}
		logger.info("Products list is present and contains {} products.", products.size());
		Allure.addAttachment("Products Count", "Products count: " + products.size());
		logger.info("Products list is present and contains products.");
	}

	@Step("Asserting that the response matches the key aspects of the products list JSON file")
	@Then("the response should match the key aspects of the products list JSON file")
	public void theResponseShouldMatchTheKeyAspectsOfTheProductsListJsonFile() {
		try {
			String actualJson = CommonSteps.response.asString();
			Gson gson = new Gson();
			JsonObject actualJsonObject = gson.fromJson(actualJson, JsonObject.class);

			Allure.addAttachment("Actual Response JSON", actualJson);

			// Check responseCode
			Assert.assertEquals(actualJsonObject.get("responseCode").getAsInt(), 200);

			// Check product list
			JsonArray actualProducts = actualJsonObject.getAsJsonArray("products");
			Allure.addAttachment("Actual Products", actualProducts.toString());
			Assert.assertTrue(actualProducts.size() > 0, "Product list should not be empty");

			for (int i = 0; i < actualProducts.size(); i++) {
				JsonObject actualProduct = actualProducts.get(i).getAsJsonObject();
				logger.info("Checking product {} details", i + 1);

				Assert.assertTrue(actualProduct.has("id"), "Product should have an id");
				logger.info("Product ID: {}", actualProduct.get("id").getAsString());

				Assert.assertTrue(actualProduct.has("name"), "Product should have a name");
				logger.info("Product Name: {}", actualProduct.get("name").getAsString());

				Assert.assertTrue(actualProduct.has("price"), "Product should have a price");
				logger.info("Product Price: {}", actualProduct.get("price").getAsString());

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An error occurred while checking the response structure", e);
			Assert.fail("An error occurred while checking the response structure");
		}
	}
}
