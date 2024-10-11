package api.stepsdef;

import api.clients.AccountApiClient;
import api.models.CreateAccountRequest;
import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigProvider;
import utils.TestDataGenerator;
import utils.UserApiHelper;

import java.util.Map;

/**
 * Step definition class for account creation and validation.
 */
public class AccountSteps {
	public static CreateAccountRequest createAccountRequest;
	private static final Logger logger = LogManager.getLogger(AccountSteps.class);

	/**
	 * Step for generating valid account data using Faker.
	 */
	@Step("Generating valid data for account creation")
	@Given("I have a valid user data for account creation")
	public void iHaveValidUserDataForAccountCreation() {
		logger.info("Generating valid data for account creation.");
		createAccountRequest = TestDataGenerator.generateValidCreateAccountRequest();
		Allure.addAttachment("Generated Account Request", createAccountRequest.toString());
	}

	/**
	 * Step for generating account data with an existing email.
	 */
	@Step("Checking if the account with existing email exists")
	@Given("I have user data with existing email for account creation")
	public void iHaveUserDataWithExistingEmailForAccountCreation() {
		logger.info("Checking if the account with existing email exists.");

		// Retrieve the existing email from config
		String existingEmail = ConfigProvider.getExistingEmail();

		// Use UserApiHelper to check if the user exists
		boolean userExists = UserApiHelper.doesUserExist(existingEmail);

		if (userExists) {
			logger.info("Account with existing email already exists.");

			// Retrieve existing user data via API
			Map<String, Object> user = UserApiHelper.getUserDataByEmail(existingEmail);

			// Create a request based on the data received from the GET-request
			createAccountRequest = TestDataGenerator.buildCreateAccountRequestFromExistingData(user);

		} else {
			// If the account is not found, create a new account with this email address
			logger.info("Account with existing email not found. Creating the account.");
			createAccountRequest = TestDataGenerator.generateCreateAccountRequestWithExistingEmail(existingEmail);
			// Send a request to create an account
			new AccountApiClient().createAccount(createAccountRequest);
		}

		// Add an existing email to the Allure report
		Allure.addAttachment("Existing Email for Account Creation", existingEmail);
	}
}
