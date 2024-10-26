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
 * Step definitions for the User Logout feature.
 */
public class LogoutSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage = new HomePage(driver);
	private LoginPage loginPage;
	private static final Logger logger = LogManager.getLogger(LogoutSteps.class);

	@Step("Logging in as a user")
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

			// Add login details to the Allure report
			Allure.addAttachment("Login Email", new ByteArrayInputStream(email.getBytes(StandardCharsets.UTF_8)));
			Allure.addAttachment("Login Password", new ByteArrayInputStream(password.getBytes(StandardCharsets.UTF_8)));

			logger.info("Logged in as user");
		} else {
			logger.info("User is already logged in");
		}
	}

	@Step("Clicking Logout link on the homepage")
	@When("I click on the \"Logout\" link on the homepage")
	public void iClickOnLogoutLinkOnTheHomePage() {
		homePage.clickLogout();
		logger.info("Clicked on Logout link");
	}

	@Step("Verifying redirection to the homepage")
	@Then("I should be redirected to the homepage")
	public void iShouldBeRedirectedToTheHomepage() {
		String currentUrl = driver.getCurrentUrl();
		Allure.addAttachment("Current URL", new ByteArrayInputStream(currentUrl.getBytes(StandardCharsets.UTF_8)));

		if (currentUrl.equals(ConfigProvider.getBaseUri() + "/")) {
			logger.info("Redirected to the homepage");
		} else {
			logger.error("Not redirected to the homepage");
		}
	}

	@Step("Verifying user is logged out")
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
