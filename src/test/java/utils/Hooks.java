package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ui.stepsdef.CommonSteps;

import java.time.Duration;
import java.util.List;

/**
 * Hooks for initializing and tearing down the WebDriver and handling screenshots for Cucumber tests.
 */
public class Hooks {
	private static final Logger logger = LogManager.getLogger(Hooks.class);

	@Before
	public void setup() {
		// Initialize the WebDriver once for the entire suite
		DriverManager.initDriver();
		logger.info("Driver initialized for the test suite.");

		// Open the base URL
		WebDriver driver = DriverManager.getDriver();
		driver.get(ConfigProvider.getBaseUri());
		logger.info("Navigated to base URL");

		// Close the popup window if it appears
		closePopupIfPresent(driver);
	}

	/**
	 * Takes a screenshot at the end of each test.
	 * If the test fails, the screenshot is attached and logged as an error.
	 * If the test passes, the screenshot is attached and logged as informational.
	 * Additionally, if the scenario is tagged with @account_create and passes, the account is deleted.
	 */
	@After
	public void teardown(Scenario scenario) {
		WebDriver driver = DriverManager.getDriver();

		// Take a screenshot immediately after the test
		if (scenario.isFailed()) {
			logger.error("Test failed: " + scenario.getName());
			ScreenshotUtil.captureAndAttachScreenshot(driver, "Failed scenario: " + scenario.getName());
		} else {
			logger.info("Test passed: " + scenario.getName());
			ScreenshotUtil.captureAndAttachScreenshot(driver, "Passed scenario: " + scenario.getName());

			// Delete the account only if the test passed and the scenario has the @account_create tag
			if (scenario.getSourceTagNames().contains("@account_create")) {
				logger.info("Executing post-condition: deleting the created account.");
				new CommonSteps().deleteCreatedAccount(); // Call the method to delete the account
			}
		}

		// Clear cookies and local storage to simulate a fresh session
		driver.manage().deleteAllCookies();
		((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
		((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");

		logger.info("Cookies and storage cleared for a clean session");

		// Optional: Navigate back to the homepage or login page
		driver.navigate().refresh();
	}

	@AfterSuite
	public void teardownSuite() {
		// Quit the WebDriver once after all tests are done
		DriverManager.quitDriver();
		logger.info("Driver quit after the test suite.");
	}

	/**
	 * Method to close the popup window if it appears.
	 */
	private void closePopupIfPresent(WebDriver driver) {
		try {
			// Wait a few seconds to make sure the window appears
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			// Wait for the popup window to appear
			List<WebElement> popups = driver.findElements(By.cssSelector("body > div > div.fc-dialog-container"));
			logger.info("Checking for popup...");

			// If the popup is found and displayed, click the button
			if (!popups.isEmpty() && popups.get(0).isDisplayed()) {
				WebElement agreeButton = driver.findElement(By.cssSelector(".fc-button-label"));
				agreeButton.click();
				logger.info("Popup window closed by clicking 'I agree' button");
			} else {
				logger.info("Popup window not found or not displayed");
			}
		} catch (Exception e) {
			// Handle exceptions if something goes wrong
			logger.warn("An error occurred while trying to close the popup window", e);
		}
	}
}
