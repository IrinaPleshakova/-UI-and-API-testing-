package ui.stepsdef;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import utils.DriverManager;
import utils.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step definitions for the User Login feature.
 */
public class LoginSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private LoginPage loginPage = new LoginPage(driver);
	private static final Logger logger = LogManager.getLogger(LoginSteps.class);

	@Given("I have an existing user account")
	public void iHaveAnExistingUserAccount() {
		String email = ConfigProvider.getValidEmail();
		String password = ConfigProvider.getValidPassword();
		loginPage.enterLoginEmail(email);
		loginPage.enterLoginPassword(password);
		logger.info("Using existing user account from config");
	}

	@When("I enter valid login credentials")
	public void iEnterValidLoginCredentials() {
		loginPage = new LoginPage(driver);
		String email = ConfigProvider.getValidEmail();
		String password = ConfigProvider.getValidPassword();
		loginPage.enterLoginEmail(email);
		loginPage.enterLoginPassword(password);
		logger.info("Entered valid login credentials");
	}

	@When("I enter invalid login credentials")
	public void iEnterInvalidLoginCredentials() {
		loginPage = new LoginPage(driver);
		String email = ConfigProvider.getInvalidEmail();
		String password = ConfigProvider.getInvalidPassword();
		loginPage.enterLoginEmail(email);
		loginPage.enterLoginPassword(password);
		logger.info("Entered invalid login credentials");
	}

	@Then("I should be logged in as the user")
	public void iShouldBeLoggedInAsTheUser() {
		boolean isLoggedIn = homePage.isLoggedIn();
		if (isLoggedIn) {
			logger.info("User is logged in");
		} else {
			logger.error("User is not logged in");
		}
	}

	@Then("I should see an error message {string}")
	public void iShouldSeeAnErrorMessage(String expectedMessage) {
		boolean isErrorDisplayed = loginPage.isIncorrectLoginMessageDisplayed();
		if (isErrorDisplayed) {
			logger.info("Error message is displayed: {}", expectedMessage);
		} else {
			logger.error("Error message is not displayed");
		}
	}
}
