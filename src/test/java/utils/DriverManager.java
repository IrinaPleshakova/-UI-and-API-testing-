package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverManager class to manage WebDriver instances and configuration for different browsers.
 */
public class DriverManager {
	private static WebDriver driver;

	/**
	 * Method to get the WebDriver instance based on configuration.
	 */
	public static WebDriver getDriver() {
		if (driver == null) {
			String browser = ConfigProvider.getBrowser(); // Retrieve browser type from configuration
			boolean headless = ConfigProvider.isHeadless(); // Check if headless mode is enabled

			switch (browser.toLowerCase()) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					ChromeOptions chromeOptions = new ChromeOptions();
					if (headless) {
						chromeOptions.addArguments("--headless");
					}
					driver = new ChromeDriver(chromeOptions);
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					if (headless) {
						firefoxOptions.addArguments("--headless");
					}
					driver = new FirefoxDriver(firefoxOptions);
					break;
				default:
					throw new RuntimeException("Unsupported browser: " + browser);
			}
			driver.manage().window().maximize();
		}
		return driver;
	}

	/**
	 * Method to quit the WebDriver instance.
	 */
	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
