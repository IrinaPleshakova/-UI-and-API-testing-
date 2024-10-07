package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * Step definitions for view all products and product filtering functionality.
 */
public class ViewAndFilterProductsSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private ProductsPage productsPage = new ProductsPage(driver);
	private final Logger logger = LogManager.getLogger(ViewAndFilterProductsSteps.class);

	@Step("Verifying that the list of products is displayed")
	@Then("I should see a list of products")
	public void iShouldSeeAListOfProducts() {
		int productCount = productsPage.getProductCount();
		Assert.assertTrue(productCount > 0, "Product list is not displayed");

		// Add the product count as an attachment in the Allure report
		Allure.addAttachment("Product Count", new ByteArrayInputStream(String.valueOf(productCount).getBytes(StandardCharsets.UTF_8)));
		logger.info("Product list is displayed with {} products", productCount);
	}

	@Step("Clicking on the 'Women' category")
	@When("I click on \"Women\" category")
	public void iClickOnWomenCategory() {
		homePage.clickWomenCategory();

		// Add the selected category as an attachment in the Allure report
		Allure.addAttachment("Category Selected", "Women");
		logger.info("Clicked on Women category");
	}

	@Step("Clicking on the 'Dress' subcategory")
	@When("I click on \"Dress\" subcategory")
	public void iClickOnDressSubcategory() {
		homePage.clickDressSubcategory();
		productsPage = new ProductsPage(driver);

		// Add the selected subcategory as an attachment in the Allure report
		Allure.addAttachment("Subcategory Selected", "Dress");
		logger.info("Clicked on Dress subcategory");
	}

	@Step("Verifying products are filtered by 'Women > Dress'")
	@Then("I should see products filtered by \"Women > Dress\"")
	public void iShouldSeeProductsFilteredByWomenDress() {
		// Verify that the URL is correct for the 'Dress' category
		Assert.assertTrue(productsPage.isOnDressCategoryPage(), "Page URL does not match the 'Women > Dress' filter");

		// Get the number of filtered products
		int filteredProductCount = productsPage.getProductCount();
		Assert.assertTrue(filteredProductCount > 0, "No products found for the 'Women > Dress' filter");

		// Verify that all displayed products are from the 'Dress' category
		Assert.assertTrue(productsPage.areAllProductsRelatedToDress(), "Not all products are from the 'Dress' category");

		// Add the filtered product count as an attachment in the Allure report
		Allure.addAttachment("Filtered Product Count", new ByteArrayInputStream(String.valueOf(filteredProductCount).getBytes(StandardCharsets.UTF_8)));
		logger.info("Filtered products are displayed with {} products", filteredProductCount);
	}
}
