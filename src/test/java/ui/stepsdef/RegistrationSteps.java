package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;
import utils.TestDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * Step definitions for the User Registration feature.
 */
public class RegistrationSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private LoginPage loginPage;
	private SignupPage signupPage;
	private AccountCreatedPage accountCreatedPage;
	private AccountDeletedPage accountDeletedPage;
	private String userName;
	private String userEmail;
	private String password;
	private static final Logger logger = LogManager.getLogger(RegistrationSteps.class);

	@Step("Entering new user name and email")
	@When("I enter a new user name and email")
	public void iEnterANewUserNameAndEmail() {
		loginPage = new LoginPage(driver);

		// Use TestDataGenerator to generate random name and email
		userName = TestDataGenerator.generateRandomFirstName();
		userEmail = TestDataGenerator.generateRandomEmail();
		loginPage.enterSignupName(userName);
		loginPage.enterSignupEmail(userEmail);

		// Add the data to the Allure report
		Allure.addAttachment("Signup Name", new ByteArrayInputStream(userName.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Signup Email", new ByteArrayInputStream(userEmail.getBytes(StandardCharsets.UTF_8)));

		logger.info("Entered signup name: {} and email: {}", userName, userEmail);
	}

	@Step("Filling out all required fields in the registration form")
	@When("I fill out all required fields in the registration form")
	public void iFillOutAllRequiredFieldsInTheRegistrationForm() {
		signupPage = new SignupPage(driver);

		// Use TestDataGenerator to fill in the registration form
		TestDataGenerator.fillRegistrationForm(signupPage);

		logger.info("Filled out registration form with Faker data");
	}

	@Step("Submitting the registration form")
	@When("I submit the registration form")
	public void iSubmitTheRegistrationForm() {
		signupPage.clickCreateAccountButton();
		logger.info("Submitted the registration form");
	}

	@Step("Verifying that the {0} message is displayed")
	@Then("I should see the {string} message")
	public void iShouldSeeTheMessage(String expectedMessage) {
		accountCreatedPage = new AccountCreatedPage(driver);
		accountDeletedPage = new AccountDeletedPage(driver);

		// Check if the expected message is displayed on either the Account Created or Account Deleted page
		boolean isCreatedMessageDisplayed = accountCreatedPage.isAccountCreatedMessageDisplayed(expectedMessage);
		boolean isDeletedMessageDisplayed = accountDeletedPage.isAccountDeletedMessageDisplayed(expectedMessage);

		// Attach the expected message to the Allure report
		Allure.addAttachment("Expected Message", new ByteArrayInputStream(expectedMessage.getBytes(StandardCharsets.UTF_8)));

		// Log and assert if the message is displayed
		if (isCreatedMessageDisplayed) {
			logger.info("Displayed message: {}", expectedMessage);
		} else if (isDeletedMessageDisplayed) {
			logger.info("Displayed message: {}", expectedMessage);
		} else {
			logger.error("Expected message not displayed.");
			Assert.fail("Expected message not displayed: " + expectedMessage);
		}
	}

	@Step("Verifying that the account deleted message is displayed")
	@Then("I should see the {string} message for account deleted")
	public void verifyAccountDeletedMessage(String expectedMessage) {
		accountDeletedPage = new AccountDeletedPage(driver);
		boolean isDeletedMessageDisplayed = accountDeletedPage.isAccountDeletedMessageDisplayed(expectedMessage);
		Assert.assertTrue(isDeletedMessageDisplayed, "Account deleted message is not displayed.");
	}

	@Step("Clicking the {0} button on the account created page")
	@When("I click on the {string} button on the account created page")
	public void iClickOnTheButtonOnTheAccountCreatedPage(String buttonName) {
		accountCreatedPage.clickContinueButton();
		logger.info("Clicked on button: " + buttonName);
	}
}
