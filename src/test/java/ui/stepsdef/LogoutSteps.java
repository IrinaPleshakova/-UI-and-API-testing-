package ui.stepsdef;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import ui.pages.*;
import utils.DriverManager;
import utils.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step definitions for the User Logout feature.
 */
public class LogoutSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private LoginPage loginPage;
	private static final Logger logger = LogManager.getLogger(LogoutSteps.class);

	@Given("I am logged in as a user")
	public void iAmLoggedInAsAUser() {
		driver.get(ConfigProvider.getBaseUri());
		if (!homePage.isLoggedIn()) {
			homePage.clickSignupLogin();
			loginPage = new LoginPage(driver);
			String email = ConfigProvider.getValidEmail();
			String password = ConfigProvider.getValidPassword();
			loginPage.enterLoginEmail(email);
			loginPage.enterLoginPassword(password);
			loginPage.clickLoginButton();
			logger.info("Logged in as user");
		} else {
			logger.info("User is already logged in");
		}
	}

	@When("I click on the \"Logout\" link on the homepage")
	public void iClickOnLogoutLinkOnTheHomePage() {
		homePage.clickLogout();
		logger.info("Clicked on Logout link");
	}

	@Then("I should be redirected to the homepage")
	public void iShouldBeRedirectedToTheHomepage() {
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl.equals(ConfigProvider.getBaseUri() + "/")) {
			logger.info("Redirected to the homepage");
		} else {
			logger.error("Not redirected to the homepage");
		}
	}

	@Then("I should not be logged in")
	public void iShouldNotBeLoggedIn() {
		boolean isLoggedIn = homePage.isLoggedIn();
		if (!isLoggedIn) {
			logger.info("User is not logged in");
		} else {
			logger.error("User is still logged in");
		}
	}
}
