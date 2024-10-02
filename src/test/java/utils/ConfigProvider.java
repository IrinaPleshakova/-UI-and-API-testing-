package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigProvider class to read configuration parameters from config.properties file.
 */
public class ConfigProvider {
	private static Properties properties = new Properties();

	static {
		try {
			// Load properties from the configuration file
			FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			// Set default values in case of an exception
			properties.setProperty("BASE_URI", "https://automationexercise.com");
			properties.setProperty("BROWSER", "chrome");
			properties.setProperty("HEADLESS", "false");
			properties.setProperty("VALID_NAME", "Valid User");
			properties.setProperty("VALID_EMAIL", "validuser@example.com");
			properties.setProperty("VALID_PASSWORD", "validpass");
			properties.setProperty("INVALID_EMAIL", "invaliduser@example.com");
			properties.setProperty("INVALID_PASSWORD", "wrongpass");
		}
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

	// Method to get valid name
	public static String getValidUserName() {
		return properties.getProperty("VALID_NAME", "Valid User");
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
}
