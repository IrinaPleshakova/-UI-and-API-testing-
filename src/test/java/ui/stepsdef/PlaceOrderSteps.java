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
import com.github.javafaker.Faker;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PlaceOrderSteps {

	private WebDriver driver = DriverManager.getDriver();
	private CheckoutPage checkoutPage = new CheckoutPage(driver);
	private PaymentPage paymentPage = new PaymentPage(driver);
	private static final Logger logger = LogManager.getLogger(PlaceOrderSteps.class);
	private Faker faker = new Faker();

	@Step("Verifying that the user is on the cart page")
	@Then("I should be on the cart page")
	public void iShouldBeOnTheCartPage() {
		String currentUrl = driver.getCurrentUrl();
		Allure.addAttachment("Current URL", new ByteArrayInputStream(currentUrl.getBytes(StandardCharsets.UTF_8)));
		Assert.assertTrue(currentUrl.contains("/view_cart"), "Not on the cart page");
		logger.info("Verified that we are on the cart page");
	}

	@Step("Clicking on 'Register/Login' button on the checkout page")
	@And("I click on \"RegisterLogin\" button on the checkout page")
	public void iClickOnRegisterLoginButtonOnCheckoutPage() {
		checkoutPage.clickRegisterLogin();
		logger.info("Clicked on 'Register/Login' button on the checkout page");
	}

	@Step("Verifying Address Details and Review Your Order sections are displayed")
	@Then("I should see Address Details and Review Your Order")
	public void iShouldSeeAddressDetailsAndReviewYourOrder() {
		List<String> addressDelivery = checkoutPage.getAddressDelivery();
		List<String> addressInvoice = checkoutPage.getAddressInvoice();
		Assert.assertNotNull(addressDelivery, "Address delivery details are not displayed");
		Assert.assertNotNull(addressInvoice, "Address invoice details are not displayed");

		// Adding address details to the Allure report
		Allure.addAttachment("Address Delivery", String.join(", ", addressDelivery));
		Allure.addAttachment("Address Invoice", String.join(", ", addressInvoice));

		logger.info("Verified that Address Details are displayed");
	}

	@Step("Entering a comment and clicking {0}")
	@When("I enter description in comment text area and click {string}")
	public void iEnterDescriptionInCommentTextAreaAndClick(String buttonName) {
		String comment = "Please deliver between 9 AM to 5 PM";
		checkoutPage.enterComment(comment);

		// Adding a comment to the Allure report
		Allure.addAttachment("Order Comment", new ByteArrayInputStream(comment.getBytes(StandardCharsets.UTF_8)));

		if (buttonName.equalsIgnoreCase("Place Order")) {
			checkoutPage.clickPlaceOrderButton();
		} else {
			throw new IllegalArgumentException("Unknown button: " + buttonName);
		}
		logger.info("Entered comment and clicked '" + buttonName + "'");
	}

	@Step("Entering payment details")
	@When("I enter payment details")
	public void iEnterPaymentDetails() {
		String nameOnCard = faker.name().fullName();
		String cardNumber = "4111111111111111";
		String cvc = "123";
		String expiryMonth = "12";
		String expiryYear = "2025";

		paymentPage.enterPaymentDetails(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear);

		// Adding payment details to the Allure report
		Allure.addAttachment("Name on Card", new ByteArrayInputStream(nameOnCard.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Card Number", new ByteArrayInputStream(cardNumber.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("CVC", new ByteArrayInputStream(cvc.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Expiry Date", expiryMonth + "/" + expiryYear);

		logger.info("Entered payment details");
	}

	@Step("Verifying that the message '{0}' is displayed")
	@Then("I should see the message {string}")
	public void iShouldSeeTheMessage(String expectedMessage) {
		boolean isDisplayed = paymentPage.isOrderConfirmationDisplayed(expectedMessage);
		Allure.addAttachment("Expected Message", new ByteArrayInputStream(expectedMessage.getBytes(StandardCharsets.UTF_8)));
		Assert.assertTrue(isDisplayed, "Expected message is not displayed");
		logger.info("Verified that the message '" + expectedMessage + "' is displayed");
	}
}
