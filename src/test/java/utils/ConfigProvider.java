package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigProvider class to load configuration parameters from different property files
 * depending on the test type (UI or API).
 */
public class ConfigProvider {
	private static Properties properties = new Properties();
	private static String configFilePath;
	private static final Logger logger = LogManager.getLogger(ConfigProvider.class);

	/**
	 * Method to set the configuration file path based on the test type.
	 * This method is executed before the test suite starts.
	 */
	@BeforeSuite
	@Parameters("testType")
	public void setConfigFile(String testType) {
		if (testType.equals("api")) {
			configFilePath = "src/test/resources/config_api.properties";  // Load API configuration
		} else {
			configFilePath = "src/test/resources/config_ui.properties";   // Load UI configuration
		}
		logger.info("Configuration file path set to: " + configFilePath);
		loadProperties();
	}

	//	 Loads the properties from the selected configuration file.
	private static void loadProperties() {
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

	//	 Sets default values in case of an error while loading the configuration file.
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
		String browser = properties.getProperty("BROWSER", "chrome");
		logger.info("Browser from config: " + browser);  // Logging browser configurations
		return browser;
	}

	// Method to check if headless mode is enabled
	public static boolean isHeadless() {
		String headlessProperty = properties.getProperty("HEADLESS", "false");
		logger.info("Headless mode: " + headlessProperty);  // Headless mode logging
		return Boolean.parseBoolean(headlessProperty);
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
		return properties.getProperty("EXISTING_PASSWORD", "Password123!");
	}

	// Methods to set and retrieve generated email and password (used for API tests)
	public static void setGeneratedEmail(String email) {
		properties.setProperty("GENERATED_EMAIL", email);
		logger.info("Generated email set to: " + email);
		saveProperties();
	}

	public static String getGeneratedEmail() {
		return properties.getProperty("GENERATED_EMAIL");
	}

	public static void setGeneratedPassword(String password) {
		properties.setProperty("GENERATED_PASSWORD", password);
		logger.info("Generated password set to: " + password);
		saveProperties();
	}

	public static String getGeneratedPassword() {
		return properties.getProperty("GENERATED_PASSWORD");
	}

	/**
	 * Saves the properties back to the configuration file.
	 * This only applies to API tests where data might change.
	 */
	private static void saveProperties() {
		if (configFilePath == null) {
			logger.error("Config file path is not set. Ensure 'setConfigFile' is called before saving properties.");
			throw new IllegalStateException("Config file path is not set. Ensure 'setConfigFile' is called before saving properties.");
		}
		if (configFilePath.contains("config_api.properties")) {
			try {
				FileOutputStream fos = new FileOutputStream(configFilePath);
				properties.store(fos, null);
				fos.close();
				logger.info("Properties saved to: " + configFilePath);
			} catch (IOException e) {
				logger.error("Failed to save properties to: " + configFilePath, e);
			}
		}
	}
}
