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
	private String removedProductName;

	@Step("Adding a random product to the cart")
	@When("I add a random product to the cart")
	public void iAddARandomProductToTheCart() {
		productsPage.navigateTo();
		String productName = productsPage.selectRandomProduct();
		// Check that the product has not been added earlier
		while (addedProductNames.contains(productName)) {
			logger.info("Product '{}' is already in the cart. Selecting another product.", productName);
			productsPage.navigateTo(); // Navigate back to the products page
			productName = productsPage.selectRandomProduct();
		}

		addedProductNames.add(productName);
		productDetailPage.clickAddToCart();
		logger.info("Added product: " + productName);
		Allure.addAttachment("Added product", new ByteArrayInputStream(productName.getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Verifying the confirmation message is displayed")
	@And("I should see a confirmation message")
	public void iShouldSeeAConfirmationMessage() {
		boolean isDisplayed = productDetailPage.isConfirmationMessageDisplayed();
		assert isDisplayed : "Confirmation message is not displayed";
		logger.info("Confirmation message is displayed");
		Allure.addAttachment("Confirmation message displayed", new ByteArrayInputStream("Displayed".getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Checking that both products are in the cart")
	@Then("I should see both products in the cart")
	public void iShouldSeeBothProductsInTheCart() {
		cartPage.navigateTo();
		List<String> productsInCart = cartPage.getProductNamesInCart();
		assert productsInCart.containsAll(addedProductNames) : "Not all added products are in the cart";
		logger.info("Both products are present in the cart");
		Allure.addAttachment("Products in cart", new ByteArrayInputStream(productsInCart.toString().getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Adding two products to the cart")
	@Given("I have two products in my cart")
	public void iHaveTwoProductsInMyCart() {
		Hooks.closePopupIfPresent(driver);
		// Add first product
		productsPage.navigateTo();
		String productName1 = productsPage.selectRandomProduct();
		addedProductNames.add(productName1);
		productDetailPage.clickAddToCart();
		productDetailPage.isConfirmationMessageDisplayed();
		productDetailPage.clickContinueShopping();
		logger.info("Added first product to the cart: {}", productName1);
		Allure.addAttachment("First added product", new ByteArrayInputStream(productName1.getBytes(StandardCharsets.UTF_8)));

		// Add second unique product
		productsPage.navigateTo();
		String productName2 = productsPage.selectRandomProduct();

		// Checking that the second product is unique
		while (addedProductNames.contains(productName2)) {
			logger.info("Product '{}' is already in the cart. Selecting another product.", productName2);
			productsPage.navigateTo(); // Navigate back to the products page
			productName2 = productsPage.selectRandomProduct();
		}

		addedProductNames.add(productName2);
		productDetailPage.clickAddToCart();
		productDetailPage.isConfirmationMessageDisplayed();
		productDetailPage.clickViewCart();
		logger.info("Added second product to the cart: {}", productName2);
		Allure.addAttachment("Second added product", new ByteArrayInputStream(productName2.getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Removing a product from the cart")
	@When("I remove one product from the cart")
	public void iRemoveOneProductFromTheCart() {
		// Log products in the cart before removal for debugging
		List<String> productsBeforeRemoval = cartPage.getProductNamesInCart();
		logger.info("Products in cart before removal: " + productsBeforeRemoval);

		// Remove the first product from the cart
		removedProductName = addedProductNames.remove(0);
		cartPage.removeProductByName(removedProductName);

		// Refresh the cart page to ensure it is updated
		cartPage.refresh();  // Adding refresh to update the cart view after removal

		// Log products in the cart after removal for debugging
		List<String> productsAfterRemoval = cartPage.getProductNamesInCart();
		logger.info("Products in cart after removal: " + productsAfterRemoval);

		Allure.addAttachment("Removed product", new ByteArrayInputStream(removedProductName.getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Verifying the removed product is not in the cart")
	@Then("I should not see that product in the cart")
	public void iShouldNotSeeThatProductInTheCart() {
		List<String> productsInCart = cartPage.getProductNamesInCart();
		assert !productsInCart.contains(removedProductName) : "Removed product is still in the cart";
		logger.info("Verified that the removed product '{}' is not in the cart", removedProductName);
		Allure.addAttachment("Removed product", new ByteArrayInputStream(removedProductName.getBytes(StandardCharsets.UTF_8)));
	}

	@Step("Verifying only the remaining product is in the cart")
	@And("I should see only the remaining product in the cart")
	public void iShouldSeeOnlyTheRemainingProductInTheCart() {
		List<String> productsInCart = cartPage.getProductNamesInCart();

		// Additional logging for debugging
		logger.info("Current products in the cart: " + productsInCart);
		logger.info("Expected remaining product(s): " + addedProductNames);

		assert productsInCart.size() == 1 : "Expected exactly one product in the cart, but found " + productsInCart.size();
		assert productsInCart.containsAll(addedProductNames) : "The remaining product in the cart is incorrect. Expected: " + addedProductNames + ", but found: " + productsInCart;
		logger.info("Verified that the cart contains only the remaining product: " + productsInCart.get(0));
		Allure.addAttachment("Final cart products", new ByteArrayInputStream(productsInCart.toString().getBytes(StandardCharsets.UTF_8)));
	}
}
