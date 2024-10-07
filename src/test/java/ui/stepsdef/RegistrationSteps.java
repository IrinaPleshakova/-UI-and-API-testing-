package ui.stepsdef;

import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.pages.*;
import utils.DriverManager;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
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

	@Step("Entering new user name and email")
	@When("I enter a new user name and email")
	public void iEnterANewUserNameAndEmail() {
		loginPage = new LoginPage(driver);
		userName = faker.name().firstName();
		userEmail = faker.internet().emailAddress();
		loginPage.enterSignupName(userName);
		loginPage.enterSignupEmail(userEmail);

		// Adding user data to Allure report
		Allure.addAttachment("Signup Name", new ByteArrayInputStream(userName.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Signup Email", new ByteArrayInputStream(userEmail.getBytes(StandardCharsets.UTF_8)));

		logger.info("Entered signup name: {} and email: {}", userName, userEmail);
	}

	@Step("Filling out all required fields in the registration form")
	@When("I fill out all required fields in the registration form")
	public void iFillOutAllRequiredFieldsInTheRegistrationForm() {
		signupPage = new SignupPage(driver);
		// Select title
		signupPage.selectTitle("Mrs");

		// Generate and enter a random password
		password = faker.internet().password();
		signupPage.enterPassword(password);

		// Adding password to Allure report
		Allure.addAttachment("Password", new ByteArrayInputStream(password.getBytes(StandardCharsets.UTF_8)));

		// Generate a random date of birth
		Date randomDate = faker.date().birthday(18, 65);
		LocalDate localDate = randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String day = String.valueOf(localDate.getDayOfMonth());
		String month = String.valueOf(localDate.getMonthValue());
		String year = String.valueOf(localDate.getYear());
		// Entering the generated date of birth
		signupPage.selectDateOfBirth(day, month, year);

		// Adding date of birth to Allure report
		Allure.addAttachment("Date of Birth", day + "/" + month + "/" + year);

		// Fill out personal details
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String company = faker.company().name();
		String address1 = faker.address().streetAddress();
		String address2 = faker.address().secondaryAddress();
		String country = "United States";
		String state = faker.address().state();
		String city = faker.address().city();
		String zipcode = faker.address().zipCode();
		String mobileNumber = faker.phoneNumber().cellPhone();

		signupPage.enterFirstName(firstName);
		signupPage.enterLastName(lastName);
		signupPage.enterCompany(company);
		signupPage.enterAddress1(address1);
		signupPage.enterAddress2(address2);
		signupPage.selectCountry(country);
		signupPage.enterState(state);
		signupPage.enterCity(city);
		signupPage.enterZipcode(zipcode);
		signupPage.enterMobileNumber(mobileNumber);

		// Adding personal details to Allure report
		Allure.addAttachment("First Name", new ByteArrayInputStream(firstName.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Last Name", new ByteArrayInputStream(lastName.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Company", new ByteArrayInputStream(company.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Address1", new ByteArrayInputStream(address1.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Address2", new ByteArrayInputStream(address2.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Country", new ByteArrayInputStream(country.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("State", new ByteArrayInputStream(state.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("City", new ByteArrayInputStream(city.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Zipcode", new ByteArrayInputStream(zipcode.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Mobile Number", new ByteArrayInputStream(mobileNumber.getBytes(StandardCharsets.UTF_8)));

		logger.info("Filled out registration form with faker data");
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

		boolean isCreatedMessageDisplayed = accountCreatedPage.isAccountCreatedMessageDisplayed(expectedMessage);
		boolean isDeletedMessageDisplayed = accountDeletedPage.isAccountDeletedMessageDisplayed(expectedMessage);

		Allure.addAttachment("Expected Message", new ByteArrayInputStream(expectedMessage.getBytes(StandardCharsets.UTF_8)));

		if (isCreatedMessageDisplayed || isDeletedMessageDisplayed) {
			logger.info("Expected message is displayed: {}", expectedMessage);
		} else {
			logger.error("Expected message not displayed.");
		}
	}

	@Step("Clicking the {0} button on the account created page")
	@When("I click on the {string} button on the account created page")
	public void iClickOnTheButtonOnTheAccountCreatedPage(String buttonName) {
		accountCreatedPage.clickContinueButton();
		logger.info("Clicked on button: " + buttonName);
	}

	@Step("Verifying the user is logged in as the new user")
	@Then("I should be logged in as the new user")
	public void iShouldBeLoggedInAsTheNewUser() {
		boolean isLoggedIn = homePage.isLoggedIn();
		if (isLoggedIn) {
			logger.info("User is logged in");
		} else {
			logger.error("User is not logged in");
		}
	}

	@Step("Verifying that the user is logged in as the new user")
	@Then("I should see \"Logged in as the new user\"")
	public void iShouldSeeLoggedInAsTheNewUser() {
		String actualUserName = homePage.getLoggedInUserName();
		Allure.addAttachment("Expected Logged in User", new ByteArrayInputStream(userName.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Actual Logged in User", new ByteArrayInputStream(actualUserName.getBytes(StandardCharsets.UTF_8)));
		logger.info("Expected Logged in User: {}, Actual Logged in User: {}", userName, actualUserName);
		Assert.assertEquals(actualUserName, userName, "The logged-in user name does not match.");
	}
}
