package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Utility class to capture screenshots during tests.
 */
public class ScreenshotUtil {

	private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

	public static void takeScreenshot(WebDriver driver, String testName) {
		// Remove invalid characters from the test name
		String sanitizedTestName = testName.replaceAll("[^a-zA-Z0-9\\s]", "").replace(" ", "_");

		// Capture the screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = "screenshots/" + sanitizedTestName + "_" + System.currentTimeMillis() + ".png";
		File destFile = new File(fileName);

		// Save the screenshot to the specified location
		try {
			FileUtils.copyFile(srcFile, destFile);
			logger.info("Screenshot taken: " + fileName);
		} catch (IOException e) {
			logger.error("Failed to take screenshot: ", e);
		}
	}

	/**
	 * Method to capture a screenshot and attach it to Allure report.
	 */
	public static void captureAndAttachScreenshot(WebDriver driver, String stepName) {
		// Capture screenshot as byte array and attach it to Allure report
		Allure.addAttachment(stepName, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		logger.info("Screenshot captured and attached to Allure report: " + stepName);
	}
}
