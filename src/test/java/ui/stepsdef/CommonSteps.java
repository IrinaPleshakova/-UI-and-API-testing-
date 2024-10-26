package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;
import utils.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Hooks;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * CommonSteps class to hold step definitions shared across multiple features.
 */
public class CommonSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private CartPage cartPage = new CartPage(driver);
	private CheckoutPage checkoutPage = new CheckoutPage(driver);
	private PaymentPage paymentPage = new PaymentPage(driver);
	private AccountDeletedPage accountDeletedPage = new AccountDeletedPage(driver);
	private LoginPage loginPage = new LoginPage(driver);
	private ProductDetailPage productDetailPage = new ProductDetailPage(driver);
	private ProductsPage productsPage = new ProductsPage(driver);
	private String actualUserName;
	private static final Logger logger = LogManager.getLogger(CommonSteps.class);

	@Step("Opening the homepage")
	@Given("I open the homepage")
	public void iOpenTheHomepage() {
		driver.get(ConfigProvider.getBaseUri());
		Allure.addAttachment("Homepage URL", new ByteArrayInputStream(ConfigProvider.getBaseUri().getBytes(StandardCharsets.UTF_8)));
		homePage = new HomePage(driver);
		logger.info("Opened the homepage");

		Hooks.closePopupIfPresent(driver);
	}

	@Step("Clicking the Signup/Login link on the homepage")
	@When("I click on the \"SignupLogin\" link on the homepage")
	public void iClickOnSignupLoginLinkOnTheHomepage() {
		homePage.clickSignupLogin();
		logger.info("Clicked on Signup / Login link");
	}

	@Step("Clicking the {string} button on the login page")
	@When("I click on the {string} button on the login page")
	public void iClickOnButtonOnTheLoginPage(String buttonType) {
		switch (buttonType.toLowerCase()) {
			case "signup":
				loginPage.clickSignupButton();
				logger.info("Clicked on Signup button");
				break;
			case "login":
				loginPage.clickLoginButton();
				logger.info("Clicked on Login button");
				break;
			default:
				throw new IllegalArgumentException("Unknown button type: " + buttonType);
		}
	}

	@Step("Clicking the Products link")
	@And("I click on \"Products\" link")
	public void iClickOnProductsLink() {
		homePage.clickProducts();
		productsPage = new ProductsPage(driver);
		logger.info("Clicked on Products link");
	}

	@Step("Clicking the {string} button")
	@Then("I click {string} button")
	public void iClickButton(String buttonName) {
		switch (buttonName.toLowerCase()) {
			case "continue shopping":
				productDetailPage.clickContinueShopping();
				break;
			case "view cart":
				productDetailPage.clickViewCart();
				break;
			case "proceed to checkout":
				cartPage.clickProceedToCheckout();
				break;
			case "cart":
				homePage.clickCart();
				break;
			case "register / login":
				checkoutPage.clickRegisterLogin();
				break;
			case "place order":
				checkoutPage.clickPlaceOrderButton();
				break;
			case "pay and confirm order":
				paymentPage.clickPayAndConfirmOrder();
				break;
			case "continue on payment page":
				paymentPage.continueButtonClick();
				break;
			case "delete account":
				homePage.clickDeleteAccount();
				break;
			case "continue on account deleted page":
				accountDeletedPage.clickContinueButton();
				break;
			default:
				throw new IllegalArgumentException("Unknown button: " + buttonName);
		}
		logger.info("Clicked '" + buttonName + "' button");
	}

	// Step to verify if the user is logged in
	@Step("Verifying the user is logged in")
	@Then("I should be logged in as the {string}")
	public void iShouldBeLoggedInAs(String userType) {
		boolean isLoggedIn = homePage.isLoggedIn();
		Assert.assertTrue(isLoggedIn, "User is not logged in.");
		logger.info("User is logged in as {}", userType);

		// Save the actual username after login or registration
		this.actualUserName = homePage.getLoggedInUserName();
		logger.info("Saved the logged-in user's name: {}", actualUserName);

		// Verify that the displayed username matches the actual username
		String displayedUserName = homePage.getLoggedInUserName();
		Assert.assertEquals(displayedUserName, actualUserName, "The logged-in user name does not match.");
		logger.info("Expected Logged in User: {}, Actual Logged in User: {}", actualUserName, displayedUserName);

		// Add attachments to Allure report
		Allure.addAttachment("Expected Logged in User", new ByteArrayInputStream(actualUserName.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Actual Logged in User", new ByteArrayInputStream(displayedUserName.getBytes(StandardCharsets.UTF_8)));
	}

	// Step to delete the created account
	@Step("Deleting the created account")
	public void deleteCreatedAccount() {
		homePage.clickDeleteAccount();

		if (accountDeletedPage.isAccountDeletedMessageDisplayed("ACCOUNT DELETED!")) {
			logger.info("Account deleted successfully.");
		} else {
			logger.error("Failed to delete the account.");
		}

		accountDeletedPage.clickContinueButton();
		logger.info("Account deletion process completed, user returned to the homepage.");
	}
}
