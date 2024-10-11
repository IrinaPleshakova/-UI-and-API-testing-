package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigProvider class to load configuration parameters from a single property file.
 */
public class ConfigProvider {
	private static Properties properties = new Properties();
	private static final String configFilePath = "src/test/resources/config.properties";  // Unified config file path
	private static final Logger logger = LogManager.getLogger(ConfigProvider.class);

	// Loads the properties from the unified configuration file.
	@BeforeSuite
	public static void loadProperties() {
		try {
			FileInputStream fis = new FileInputStream(configFilePath);
			properties.load(fis);
			fis.close();
			logger.info("Properties loaded from: " + configFilePath);
		} catch (IOException e) {
			logger.error("Failed to load properties from: " + configFilePath, e);
			setDefaultValues();
		}
	}

	// Sets default values in case of an error while loading the configuration file.
	private static void setDefaultValues() {
		properties.setProperty("BASE_URI", "https://automationexercise.com");
		properties.setProperty("BROWSER", "chrome");
		properties.setProperty("HEADLESS", "false");
		properties.setProperty("VALID_EMAIL", "validuser@example.com");
		properties.setProperty("VALID_PASSWORD", "validpass");
		properties.setProperty("INVALID_EMAIL", "invaliduser@example.com");
		properties.setProperty("INVALID_PASSWORD", "wrongpass");
		properties.setProperty("EXISTING_EMAIL", "existinguser@example.com");
		properties.setProperty("EXISTING_PASSWORD", "qwerty123");
		logger.info("Default values set for properties.");
	}

	// Method to get the Base URI from the configuration
	public static String getBaseUri() {
		return properties.getProperty("BASE_URI", "https://automationexercise.com");
	}

	// Method to get the browser type from the configuration
	public static String getBrowser() {
		return properties.getProperty("BROWSER", "chrome");
	}

	// Method to check if headless mode is enabled
	public static boolean isHeadless() {
		return Boolean.parseBoolean(properties.getProperty("HEADLESS", "false"));
	}

	// Method to get valid email
	public static String getValidEmail() {
		return properties.getProperty("VALID_EMAIL", "validuser@example.com");
	}

	// Method to get valid password
	public static String getValidPassword() {
		return properties.getProperty("VALID_PASSWORD", "validpass");
	}

	// Method to get invalid email
	public static String getInvalidEmail() {
		return properties.getProperty("INVALID_EMAIL", "invaliduser@example.com");
	}

	// Method to get invalid password
	public static String getInvalidPassword() {
		return properties.getProperty("INVALID_PASSWORD", "wrongpass");
	}

	// Method to get existing email
	public static String getExistingEmail() {
		return properties.getProperty("EXISTING_EMAIL", "existinguser@example.com");
	}

	// Method to get existing password
	public static String getExistingPassword() {
		return properties.getProperty("EXISTING_PASSWORD", "qwerty123");
	}
}

