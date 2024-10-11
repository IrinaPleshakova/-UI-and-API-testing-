package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * Step definitions for product detail view functionality.
 */

public class ProductDetailsInformationSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private ProductsPage productsPage = new ProductsPage(driver);
	private ProductDetailPage productDetailPage;
	private final Logger logger = LogManager.getLogger(ProductDetailsInformationSteps.class);

	//	@Step("Clicking on {0} button at the first product")
//	@When("I click on button {string} at the first product")
//	public void iClickOnButtonAtFirstProduct(String buttonText) {
//		if (buttonText.equals("View Product")) {
//			productsPage.closeBannerIfPresent();  // Close any popup/banner if present
//			productsPage.scrollToProduct(0); // Scroll to product with index 0 and click
//			productDetailPage = new ProductDetailPage(driver);
//			logger.info("Clicked on the 'View Product' button for the first product");
//		} else {
//			logger.error("Unsupported button: " + buttonText);
//			throw new IllegalArgumentException("Unsupported button: " + buttonText);
//		}
//	}
	@Step("Clicking on {0} button at the first product")
	@When("I click on button {string} at the first product")
	public void iClickOnButtonAtFirstProduct(String buttonText) {
		if (buttonText.equals("View Product")) {
//			productsPage.closeBannerIfPresent();  // Close any popup/banner if present
			productsPage.clickViewProduct(0);  // method to click on the first product
			productDetailPage = new ProductDetailPage(driver);
			logger.info("Clicked on the 'View Product' button for the first product");
		} else {
			logger.error("Unsupported button: " + buttonText);
			throw new IllegalArgumentException("Unsupported button: " + buttonText);
		}
	}


	@Step("Verifying that product details are displayed")
	@Then("I should see product name, price, availability, condition, and brand")
	public void iShouldSeeProductDetails() {
		Assert.assertNotNull(productDetailPage.getProductName(), "Product name is not present");
		Assert.assertNotNull(productDetailPage.getProductPrice(), "Product price is not present");
		Assert.assertNotNull(productDetailPage.getProductCategory(), "Product category is not present");
		Assert.assertNotNull(productDetailPage.getProductAvailability(), "Product availability is not present");
		Assert.assertNotNull(productDetailPage.getProductCondition(), "Product condition is not present");
		Assert.assertNotNull(productDetailPage.getProductBrand(), "Product brand is not present");

		Allure.addAttachment("Product Name", new ByteArrayInputStream(productDetailPage.getProductName().getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Product Price", new ByteArrayInputStream(productDetailPage.getProductPrice().getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Product Availability", new ByteArrayInputStream(productDetailPage.getProductAvailability().getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Product Condition", new ByteArrayInputStream(productDetailPage.getProductCondition().getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Product Brand", new ByteArrayInputStream(productDetailPage.getProductBrand().getBytes(StandardCharsets.UTF_8)));

		logger.info("Product details are displayed correctly");
	}
}
