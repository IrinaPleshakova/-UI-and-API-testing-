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
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	/**
	 * Initializes the WebDriver instance based on configuration.
	 */
	public static void initDriver() {
		if (driver.get() == null) {
			// Using system variables for browser and headless mode
			String browser = System.getProperty("BROWSER", ConfigProvider.getBrowser()); // Get the browser from a system property or configuration
			boolean headless = Boolean.parseBoolean(System.getProperty("HEADLESS", String.valueOf(ConfigProvider.isHeadless()))); // Get headless from a system property or configuration

			switch (browser.toLowerCase()) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					ChromeOptions chromeOptions = new ChromeOptions();
					if (headless) {
						chromeOptions.addArguments("--headless");
						chromeOptions.addArguments("--disable-gpu"); // For more stable operation in headless mode
					}
					driver.set(new ChromeDriver(chromeOptions));
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					if (headless) {
						firefoxOptions.addArguments("--headless");
					}
					driver.set(new FirefoxDriver(firefoxOptions));
					break;
				default:
					throw new RuntimeException("Unsupported browser: " + browser);
			}
			driver.get().manage().window().maximize();
		}
	}

	/**
	 * Method to get the WebDriver instance.
	 */
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			throw new RuntimeException("Driver not initialized. Call initDriver() before getDriver().");
		}
		return driver.get();
	}

	/**
	 * Method to quit the WebDriver instance.
	 */
	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
}
