package ui.stepsdef;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step definitions for view all products and product filtering functionality.
 */

public class ViewAndFilterProductsSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private ProductsPage productsPage = new ProductsPage(driver);
	private final Logger logger = LogManager.getLogger(ViewAndFilterProductsSteps.class);

	@Then("I should see a list of products")
	public void iShouldSeeAListOfProducts() {
		Assert.assertTrue(productsPage.getProductCount() > 0);
		logger.info("Product list is displayed");
	}

	@When("I click on \"Women\" category")
	public void iClickOnWomenCategory() {
		homePage.clickWomenCategory();
		logger.info("Clicked on Women category");
	}

	@When("I click on \"Dress\" subcategory")
	public void iClickOnDressSubcategory() {
		homePage.clickDressSubcategory();
		productsPage = new ProductsPage(driver);
		logger.info("Clicked on Dress subcategory");
	}

	@Then("I should see products filtered by \"Women > Dress\"")
	public void iShouldSeeProductsFilteredByWomenDress() {
		Assert.assertTrue(productsPage.getProductCount() > 0);
		logger.info("Filtered products are displayed");
	}
}
