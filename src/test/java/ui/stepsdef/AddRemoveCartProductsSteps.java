package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AddRemoveCartProductsSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private ProductsPage productsPage = new ProductsPage(driver);
	private ProductDetailPage productDetailPage = new ProductDetailPage(driver);
	private CartPage cartPage = new CartPage(driver);
	private static final Logger logger = LogManager.getLogger(AddRemoveCartProductsSteps.class);

	private List<String> addedProductNames = new ArrayList<>();
	private String removedProductName;

	@Step("Adding a random product to the cart")
	@When("I add a random product to the cart")
	public void iAddARandomProductToTheCart() {
		productsPage.navigateTo();
		String productName = productsPage.selectRandomProduct();
		addedProductNames.add(productName);
		productDetailPage.clickAddToCart();
		logger.info("Added product: " + productName);
	}

	@Step("Verifying the confirmation message is displayed")
	@And("I should see a confirmation message")
	public void iShouldSeeAConfirmationMessage() {
		boolean isDisplayed = productDetailPage.isConfirmationMessageDisplayed();
		assert isDisplayed : "Confirmation message is not displayed";
		logger.info("Confirmation message is displayed");
	}

	@Step("Checking that both products are in the cart")
	@Then("I should see both products in the cart")
	public void iShouldSeeBothProductsInTheCart() {
		cartPage.navigateTo();
		List<String> productsInCart = cartPage.getProductNamesInCart();
		assert productsInCart.containsAll(addedProductNames) : "Not all added products are in the cart";
		logger.info("Both products are present in the cart");
	}

	@Step("Adding two products to the cart")
	@Given("I have two products in my cart")
	public void iHaveTwoProductsInMyCart() {
		// Add first product
		productsPage.navigateTo();
		String productName1 = productsPage.selectRandomProduct();
		addedProductNames.add(productName1);
		productDetailPage.clickAddToCart();
		productDetailPage.isConfirmationMessageDisplayed();
		productDetailPage.clickContinueShopping();
		logger.info("Added first product to the cart: {}", productName1);
		// Add second product
		productsPage.navigateTo();
		String productName2 = productsPage.selectRandomProduct();
		addedProductNames.add(productName2);
		productDetailPage.clickAddToCart();
		productDetailPage.isConfirmationMessageDisplayed();
		productDetailPage.clickViewCart();
		logger.info("Added second product to the cart: {}", productName2);
	}

	@Step("Removing a product from the cart")
	@When("I remove one product from the cart")
	public void iRemoveOneProductFromTheCart() {
		// Remove the first product from the cart
		removedProductName = addedProductNames.remove(0);
		cartPage.removeProductByName(removedProductName);
		logger.info("Removed product from cart: {}", removedProductName);
	}

	@Step("Verifying the removed product is not in the cart")
	@Then("I should not see that product in the cart")
	public void iShouldNotSeeThatProductInTheCart() {
		List<String> productsInCart = cartPage.getProductNamesInCart();
		assert !productsInCart.contains(removedProductName) : "Removed product is still in the cart";
		logger.info("Verified that the removed product '{}' is not in the cart", removedProductName);
	}

	@Step("Verifying only the remaining product is in the cart")
	@And("I should see only the remaining product in the cart")
	public void iShouldSeeOnlyTheRemainingProductInTheCart() {
		List<String> productsInCart = cartPage.getProductNamesInCart();
		assert productsInCart.size() == 1 : "Cart does not contain exactly one product";
		assert productsInCart.containsAll(addedProductNames) : "Remaining product is not in the cart";
		logger.info("Verified that the cart contains only the remaining product");
	}
}
