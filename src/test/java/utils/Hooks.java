package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.stepsdef.CommonSteps;

import java.time.Duration;
import java.util.List;

/**
 * Hooks for initializing and tearing down the WebDriver and handling screenshots for Cucumber tests.
 */

public class Hooks {
	private WebDriver driver;
	private static final Logger logger = LogManager.getLogger(Hooks.class);

	@Before
	public void setup() {
		// Initialize the WebDriver
		DriverManager.initDriver();
		driver = DriverManager.getDriver();
		logger.info("Driver initialized");

		// Open the base URL
		driver.get(ConfigProvider.getBaseUri());
		logger.info("Navigated to base URL");

		// Close the popup window if it appears
		closePopupIfPresent();
	}

	/**
	 * Takes a screenshot at the end of each test.
	 * If the test fails, the screenshot is attached and logged as an error.
	 * If the test passes, the screenshot is attached and logged as informational.
	 * Additionally, if the scenario is tagged with @account and passes, the account is deleted.
	 * Finally, the browser is closed after all actions are completed.
	 */
	@After
	public void teardown(Scenario scenario) {
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

		// Quit the driver after all actions are completed
		DriverManager.quitDriver();
		logger.info("Driver quit");
	}

	/**
	 * Method to close the popup window if it appears.
	 */
	private void closePopupIfPresent() {
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
