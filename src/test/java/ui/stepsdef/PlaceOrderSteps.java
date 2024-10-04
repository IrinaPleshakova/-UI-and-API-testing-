package ui.stepsdef;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.javafaker.Faker;

import java.util.List;

public class PlaceOrderSteps {

	private WebDriver driver = DriverManager.getDriver();
	private CheckoutPage checkoutPage = new CheckoutPage(driver);
	private PaymentPage paymentPage = new PaymentPage(driver);
	private static final Logger logger = LogManager.getLogger(PlaceOrderSteps.class);
	private Faker faker = new Faker();

	@Then("I should be on the cart page")
	public void iShouldBeOnTheCartPage() {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("/view_cart"), "Not on the cart page");
		logger.info("Verified that we are on the cart page");
	}

	@And("I click on \"RegisterLogin\" button on the checkout page")
	public void iClickOnRegisterLoginButtonOnCheckoutPage() {
		checkoutPage.clickRegisterLogin();
		logger.info("Clicked on 'Register/Login' button on the checkout page");
	}

	@Then("I should see Address Details and Review Your Order")
	public void iShouldSeeAddressDetailsAndReviewYourOrder() {
		List<String> addressDelivery = checkoutPage.getAddressDelivery();
		List<String> addressInvoice = checkoutPage.getAddressInvoice();
		Assert.assertNotNull(addressDelivery, "Address delivery details are not displayed");
		Assert.assertNotNull(addressInvoice, "Address invoice details are not displayed");
		logger.info("Verified that Address Details are displayed");
	}

	@When("I enter description in comment text area and click {string}")
	public void iEnterDescriptionInCommentTextAreaAndClick(String buttonName) {
		checkoutPage.enterComment("Please deliver between 9 AM to 5 PM");
		if (buttonName.equalsIgnoreCase("Place Order")) {
			checkoutPage.clickPlaceOrderButton();
		} else {
			throw new IllegalArgumentException("Unknown button: " + buttonName);
		}
		logger.info("Entered comment and clicked '" + buttonName + "'");
	}

	@When("I enter payment details")
	public void iEnterPaymentDetails() {
		String nameOnCard = faker.name().fullName();
		String cardNumber = "4111111111111111";
		String cvc = "123";
		String expiryMonth = "12";
		String expiryYear = "2025";
		paymentPage.enterPaymentDetails(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear);
		logger.info("Entered payment details");
	}

	@Then("I should see the message {string}")
	public void iShouldSeeTheMessage(String expectedMessage) {
		boolean isDisplayed = paymentPage.isOrderConfirmationDisplayed(expectedMessage);
		Assert.assertTrue(isDisplayed, "Expected message is not displayed");
		logger.info("Verified that the message '" + expectedMessage + "' is displayed");
	}
}
