package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Hooks;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AddRemoveCartProductsSteps {

	private WebDriver driver = DriverManager.getDriver();
	private ProductsPage productsPage = new ProductsPage(driver);
	private ProductDetailPage productDetailPage = new ProductDetailPage(driver);
	private CartPage cartPage = new CartPage(driver);
	private static final Logger logger = LogManager.getLogger(AddRemoveCartProductsSteps.class);

	private List<String> addedProductNames = new ArrayList<>();
	private List<String> removedProductNames = new ArrayList<>();

	private void addProductsToCart(int numberOfProducts) {
		addedProductNames.clear();
		for (int i = 0; i < numberOfProducts; i++) {
			productsPage.navigateTo();
			String productName = productsPage.selectRandomProduct();
			while (addedProductNames.contains(productName)) {
				logger.info("Product '{}' is already in the cart. Selecting another product.", productName);
				productsPage.navigateTo();
				productName = productsPage.selectRandomProduct();
			}
			addedProductNames.add(productName);
			productDetailPage.clickAddToCart();
			logger.info("Added product: " + productName);
			Allure.addAttachment("Added product", new ByteArrayInputStream(productName.getBytes(StandardCharsets.UTF_8)));

			verifyConfirmationMessage();

			if (i < numberOfProducts - 1) {
				productDetailPage.clickContinueShopping();
			}
		}
	}

	@Step("Adding {numberOfProducts} random products to the cart")
	@When("I add {int} unique random products to the cart")
	public void iAddUniqueRandomProductsToTheCart(int numberOfProducts) {
		addProductsToCart(numberOfProducts);
	}

	@Step("Adding {numberOfProducts} unique products to the cart for testing")
	@Given("I have {int} unique products in my cart")
	public void iHaveProductsInMyCart(int numberOfProducts) {
		Hooks.closePopupIfPresent(driver);
		addProductsToCart(numberOfProducts);
		productDetailPage.clickViewCart();
	}

	private void verifyConfirmationMessage() {
		boolean isDisplayed = productDetailPage.isConfirmationMessageDisplayed();
		assert isDisplayed : "Confirmation message is not displayed";
		logger.info("Confirmation message is displayed");
	}

	@Step("Verifying the confirmation message is displayed")
	@And("I should see a confirmation message")
	public void iShouldSeeAConfirmationMessage() {
		verifyConfirmationMessage();
		Allure.addAttachment("Confirmation message displayed", new ByteArrayInputStream("Displayed".getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Verifying that all added products are in the cart")
	@Then("I should see all added products in the cart")
	public void iShouldSeeAllAddedProductsInTheCart() {
		cartPage.navigateTo();
		List<String> productsInCart = cartPage.getProductNamesInCart();

		assert productsInCart.size() == addedProductNames.size() :
				"Expected " + addedProductNames.size() + " products in the cart, but found " + productsInCart.size();

		assert productsInCart.containsAll(addedProductNames) :
				"Not all added products are in the cart. Expected: " + addedProductNames + ", but found: " + productsInCart;

		logger.info("Verified that all added products are present in the cart");
		Allure.addAttachment("Products in cart", new ByteArrayInputStream(productsInCart.toString().getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Removing {int} product(s) from the cart")
	@When("I remove {int} product(s) from the cart")
	public void iRemoveProductsFromTheCart(int numberOfProductsToRemove) {
		List<String> productsInCart = cartPage.getProductNamesInCart();

		// Verify that the number of products to remove is not greater than the number of products in the cart
		if (numberOfProductsToRemove >= productsInCart.size()) {
			String errorMessage = "Invalid operation: You cannot remove the same or more products than added. " +
					"Attempted to remove " + numberOfProductsToRemove + " product(s), but only " + productsInCart.size() + " product(s) are in the cart.";
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		removedProductNames.clear();

		for (int i = 0; i < numberOfProductsToRemove; i++) {
			// Remove the first product from the cart
			String removedProductName = addedProductNames.remove(0);
			cartPage.removeProductByName(removedProductName);
			removedProductNames.add(removedProductName);

			logger.info("Removed product: " + removedProductName);
			Allure.addAttachment("Removed product", new ByteArrayInputStream(removedProductName.getBytes(StandardCharsets.UTF_8)));
		}

		List<String> productsAfterRemoval = cartPage.getProductNamesInCart();
		logger.info("Products in cart after removal: " + productsAfterRemoval);
	}

	@Step("Verifying the removed product(s) is/are not in the cart")
	@Then("I should not see the removed product(s) in the cart")
	public void iShouldNotSeeRemovedProductsInTheCart() {
		List<String> productsInCart = cartPage.getProductNamesInCart();

		// Verify that the removed products are not in the cart
		for (String removedProductName : removedProductNames) {
			assert !productsInCart.contains(removedProductName) : "Removed product '" + removedProductName + "' is still in the cart";
			logger.info("Verified that the removed product '{}' is not in the cart", removedProductName);
		}
		Allure.addAttachment("Removed products", new ByteArrayInputStream(removedProductNames.toString().getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Verifying only the remaining products are in the cart")
	@And("I should see only the remaining products in the cart")
	public void iShouldSeeOnlyRemainingProductsInTheCart() {
		List<String> productsInCart = cartPage.getProductNamesInCart();

		logger.info("Current products in the cart: " + productsInCart);
		logger.info("Expected remaining product(s): " + addedProductNames);

		assert productsInCart.size() == addedProductNames.size() : "Expected " + addedProductNames.size() + " product(s) in the cart, but found " + productsInCart.size();
		assert productsInCart.containsAll(addedProductNames) : "The remaining products in the cart are incorrect. Expected: " + addedProductNames + ", but found: " + productsInCart;
		logger.info("Verified that the cart contains only the remaining products");
		Allure.addAttachment("Final cart products", new ByteArrayInputStream(productsInCart.toString().getBytes(StandardCharsets.UTF_8)));
	}
}

