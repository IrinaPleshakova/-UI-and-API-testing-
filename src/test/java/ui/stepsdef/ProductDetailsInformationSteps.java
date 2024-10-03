package ui.stepsdef;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;

/**
 * Step definitions for product detail view functionality.
 */

public class ProductDetailsInformationSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private ProductsPage productsPage = new ProductsPage(driver);
	private ProductDetailPage productDetailPage;
	private final Logger logger = LogManager.getLogger(ProductDetailsInformationSteps.class);

	@When("I click on button {string} at the first product")
	public void iClickOnButtonAtFirstProduct(String buttonText) {
		// If the button is "View Product", click on the first product
		if (buttonText.equals("View Product")) {
			productsPage.closeBannerIfPresent();  // Close any popup/banner if present
			productsPage.scrollToProduct(0);// Scroll to product with index 0 and click
			productDetailPage = new ProductDetailPage(driver);
			logger.info("Clicked on the 'View Product' button for the first product");
		} else {
			logger.error("Unsupported button: " + buttonText);
			throw new IllegalArgumentException("Unsupported button: " + buttonText);
		}
	}

	@Then("I should see product name, price, availability, condition, and brand")
	public void iShouldSeeProductDetails() {
		Assert.assertNotNull(productDetailPage.getProductName(), "Product name is not present");
		Assert.assertNotNull(productDetailPage.getProductPrice(), "Product price is not present");
		Assert.assertNotNull(productDetailPage.getProductCategory(), "Product category is not present");
		Assert.assertNotNull(productDetailPage.getProductAvailability(), "Product availability is not present");
		Assert.assertNotNull(productDetailPage.getProductCondition(), "Product condition is not present");
		Assert.assertNotNull(productDetailPage.getProductBrand(), "Product brand is not present");
		logger.info("Product details are displayed correctly");
	}
}
