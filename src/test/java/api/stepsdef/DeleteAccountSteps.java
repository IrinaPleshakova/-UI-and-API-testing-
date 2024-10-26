package api.stepsdef;

import api.clients.AccountApiClient;
import api.models.CreateAccountRequest;
import io.cucumber.java.After;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import utils.ConfigProvider;
import io.cucumber.java.en.Given;
import utils.TestDataGenerator;

/**
 * Step definition class for account deletion API steps.
 */
public class DeleteAccountSteps {

	private static final Logger logger = LogManager.getLogger(DeleteAccountSteps.class);
	public static String email;
	public static String password;

	@Step("Creating an account to delete")
	@Given("I have a created account to delete")
	public void iHaveCreatedAccountToDelete() {
		logger.info("Creating an account for deletion.");

		// Generating a new account
		CreateAccountRequest createAccountRequest = TestDataGenerator.generateValidCreateAccountRequest();
		AccountApiClient client = new AccountApiClient();

		// Sending a request to create an account
		client.createAccount(createAccountRequest);

		// Saving generated data to variables
		email = createAccountRequest.getEmail();
		password = createAccountRequest.getPassword();

		// Logging and saving data in Allure
		logger.info("Generated email: " + email);
		logger.info("Generated password: " + password);
		Allure.addAttachment("Email for Account", email);
		Allure.addAttachment("Password for Account", password);

	}

	@Step("Setting invalid credentials for account deletion")
	@Given("I have invalid credentials for account deletion")
	public void iHaveInvalidCredentialsForAccountDeletion() {
		logger.info("Setting invalid credentials for account deletion.");
		email = ConfigProvider.getInvalidEmail();  // Using invalid email from config
		password = ConfigProvider.getInvalidPassword();  // Using invalid password from config
		Allure.addAttachment("Invalid Email for Deletion", email);
		Allure.addAttachment("Invalid Password for Deletion", password);
	}

	// Hidden account deletion step after each test
	@After("@account_create")
	public void cleanupAccount() {
		if (email != null && password != null) {
			logger.info("Deleting created account with email: {}", email);
			AccountApiClient client = new AccountApiClient();
			client.deleteAccount(email, password);
			logger.info("Account deleted successfully.");
		} else {
			logger.warn("No valid account found for deletion.");
		}
	}
}