package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import utils.DriverManager;
import utils.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private AccountCreatedPage accountCreatedPage = new AccountCreatedPage(driver);
	private AccountDeletedPage accountDeletedPage = new AccountDeletedPage(driver);
	private LoginPage loginPage = new LoginPage(driver);
	private SignupPage signupPage = new SignupPage(driver);
	private ProductDetailPage productDetailPage = new ProductDetailPage(driver);
	private ProductsPage productsPage = new ProductsPage(driver);
	private static final Logger logger = LogManager.getLogger(CommonSteps.class);

	@Step("Opening the homepage")
	@Given("I open the homepage")
	public void iOpenTheHomepage() {
		driver.get(ConfigProvider.getBaseUri());
		Allure.addAttachment("Homepage URL", new ByteArrayInputStream(ConfigProvider.getBaseUri().getBytes(StandardCharsets.UTF_8)));
		homePage = new HomePage(driver);
		logger.info("Opened the homepage");
	}

	@Step("Clicking the Signup/Login link on the homepage")
	@When("I click on the \"SignupLogin\" link on the homepage")
	public void iClickOnSignupLoginLinkOnTheHomepage() {
		homePage.clickSignupLogin();
		logger.info("Clicked on Signup / Login link");
	}

	@Step("Clicking the Signup button on the login page")
	@When("I click on the \"Signup\" button on the login page")
	public void iClickOnSignupButtonOnTheLoginPage() {
		loginPage.clickSignupButton();
		logger.info("Clicked on Signup button");
	}

	@Step("Clicking the Login button on the login page")
	@And("I click on the \"Login\" button on the login page")
	public void iClickOnTheLoginButtonOnTheLoginPage() {
		logger.info("Clicking login button");
		loginPage.clickLoginButton();
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
}
