package ui.stepsdef;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
	private Faker faker = new Faker();
	private String userName;
	private String userEmail;
	private String password;
	private static final Logger logger = LogManager.getLogger(RegistrationSteps.class);

	@When("I enter a new user name and email")
	public void iEnterANewUserNameAndEmail() {
		loginPage = new LoginPage(driver);
		userName = faker.name().firstName();
		userEmail = faker.internet().emailAddress();
		loginPage.enterSignupName(userName);
		loginPage.enterSignupEmail(userEmail);
		logger.info("Entered signup name: {} and email: {}", userName, userEmail);
	}

	@When("I fill out all required fields in the registration form")
	public void iFillOutAllRequiredFieldsInTheRegistrationForm() {
		signupPage = new SignupPage(driver);
		// Select title
		signupPage.selectTitle("Mrs");
		// Generate a random password
		password = faker.internet().password();
		signupPage.enterPassword(password);
		// Generate a random date of birth
		Date randomDate = faker.date().birthday(18, 65);
		LocalDate localDate = randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String day = String.valueOf(localDate.getDayOfMonth());
		String month = String.valueOf(localDate.getMonthValue());
		String year = String.valueOf(localDate.getYear());
		// Entering the generated date of birth
		signupPage.selectDateOfBirth(day, month, year);
		// Check newsletter and special offers
		signupPage.checkNewsletter();
		signupPage.checkSpecialOffers();
		// Enter personal details
		signupPage.enterFirstName(faker.name().firstName());
		signupPage.enterLastName(faker.name().lastName());
		signupPage.enterCompany(faker.company().name());
		signupPage.enterAddress1(faker.address().streetAddress());
		signupPage.enterAddress2(faker.address().secondaryAddress());
		signupPage.selectCountry("United States");
		signupPage.enterState(faker.address().state());
		signupPage.enterCity(faker.address().city());
		signupPage.enterZipcode(faker.address().zipCode());
		signupPage.enterMobileNumber(faker.phoneNumber().cellPhone());
		logger.info("Filled out registration form with faker data");
	}

	@When("I submit the registration form")
	public void iSubmitTheRegistrationForm() {
		signupPage.clickCreateAccountButton();
		logger.info("Submitted the registration form");
	}

	@Then("I should see the {string} message")
	public void iShouldSeeTheMessage(String expectedMessage) {
		accountCreatedPage = new AccountCreatedPage(driver);
		accountDeletedPage = new AccountDeletedPage(driver);

		boolean isCreatedMessageDisplayed = accountCreatedPage.isAccountCreatedMessageDisplayed(expectedMessage);
		boolean isDeletedMessageDisplayed = accountDeletedPage.isAccountDeletedMessageDisplayed(expectedMessage);

		if (isCreatedMessageDisplayed || isDeletedMessageDisplayed) {
			logger.info("Expected message is displayed: {}", expectedMessage);
		} else {
			logger.error("Expected message not displayed.");
		}
	}

	@When("I click on the {string} button on the account created page")
	public void iClickOnTheButtonOnTheAccountCreatedPage(String buttonName) {
		accountCreatedPage.clickContinueButton();
		logger.info("Clicked on button: " + buttonName);
	}

	@Then("I should be logged in as the new user")
	public void iShouldBeLoggedInAsTheNewUser() {
		boolean isLoggedIn = homePage.isLoggedIn();
		if (isLoggedIn) {
			logger.info("User is logged in");
		} else {
			logger.error("User is not logged in");
		}
	}
}
