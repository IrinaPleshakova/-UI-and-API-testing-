package api.stepsdef;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.qameta.allure.Step;
import utils.ConfigProvider;
import io.cucumber.java.en.Given;

/**
 * Step definition class for account deletion API steps.
 */
public class DeleteAccountSteps {

	private static final Logger logger = LogManager.getLogger(DeleteAccountSteps.class);
	public static String email;
	public static String password;

	@Step("Fetching valid credentials for account deletion from config")
	@Given("I have valid credentials for account deletion")
	public void iHaveValidCredentialsForAccountDeletion() {
		logger.info("Fetching valid email and password for account deletion from config.");
		email = ConfigProvider.getGeneratedEmail();  // Fetching generated email
		password = ConfigProvider.getGeneratedPassword();  // Fetching generated password
		logger.info("Valid credentials for deletion - Email: " + email);
	}

	@Step("Setting invalid credentials for account deletion")
	@Given("I have invalid credentials for account deletion")
	public void iHaveInvalidCredentialsForAccountDeletion() {
		logger.info("Setting invalid credentials for account deletion.");
		email = ConfigProvider.getInvalidEmail();  // Using invalid email from config
		password = ConfigProvider.getInvalidPassword();  // Using invalid password from config
	}
}