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
 * Step definitions for the User Login feature.
 */
public class LoginSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private LoginPage loginPage = new LoginPage(driver);
	private static final Logger logger = LogManager.getLogger(LoginSteps.class);

	@Step("Using an existing user account")
	@Given("I have an existing user account")
	public void iHaveAnExistingUserAccount() {
		String email = ConfigProvider.getValidEmail();
		String password = ConfigProvider.getValidPassword();
		loginPage.enterLoginEmail(email);
		loginPage.enterLoginPassword(password);

		// Adding login details to Allure report
		Allure.addAttachment("Login Email", new ByteArrayInputStream(email.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Login Password", new ByteArrayInputStream(password.getBytes(StandardCharsets.UTF_8)));

		logger.info("Using existing user account from config");
	}

	@Step("Entering valid login credentials")
	@When("I enter valid login credentials")
	public void iEnterValidLoginCredentials() {
		String email = ConfigProvider.getValidEmail();
		String password = ConfigProvider.getValidPassword();
		loginPage.enterLoginEmail(email);
		loginPage.enterLoginPassword(password);

		// Adding valid credentials to Allure report
		Allure.addAttachment("Login Email", new ByteArrayInputStream(email.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Login Password", new ByteArrayInputStream(password.getBytes(StandardCharsets.UTF_8)));

		logger.info("Entered valid login credentials");
	}

	@Step("Entering invalid login credentials")
	@When("I enter invalid login credentials")
	public void iEnterInvalidLoginCredentials() {
		String email = ConfigProvider.getInvalidEmail();
		String password = ConfigProvider.getInvalidPassword();
		loginPage.enterLoginEmail(email);
		loginPage.enterLoginPassword(password);

		// Adding invalid credentials to Allure report
		Allure.addAttachment("Login Email", new ByteArrayInputStream(email.getBytes(StandardCharsets.UTF_8)));
		Allure.addAttachment("Login Password", new ByteArrayInputStream(password.getBytes(StandardCharsets.UTF_8)));

		logger.info("Entered invalid login credentials");
	}

	@Step("Verifying user is logged in")
	@Then("I should be logged in as the user")
	public void iShouldBeLoggedInAsTheUser() {
		boolean isLoggedIn = homePage.isLoggedIn();
		if (isLoggedIn) {
			logger.info("User is logged in");
		} else {
			logger.error("User is not logged in");
		}
	}

	@Step("Verifying error message is displayed: {0}")
	@Then("I should see an error message {string}")
	public void iShouldSeeAnErrorMessage(String expectedMessage) {
		boolean isErrorDisplayed = loginPage.isIncorrectLoginMessageDisplayed();
		Allure.addAttachment("Expected Error Message", expectedMessage);

		if (isErrorDisplayed) {
			logger.info("Error message is displayed: {}", expectedMessage);
		} else {
			logger.error("Error message is not displayed");
		}
	}
}
