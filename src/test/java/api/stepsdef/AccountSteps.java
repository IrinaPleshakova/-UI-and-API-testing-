package api.stepsdef;

import api.models.CreateAccountRequest;
import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TestDataGenerator;

/**
 * Step definition class for account creation API steps.
 */
public class AccountSteps {
	public static CreateAccountRequest createAccountRequest;
	private static final Logger logger = LogManager.getLogger(AccountSteps.class);

	@Step("Generating valid data for account creation")
	@Given("I have a valid user data for account creation")
	public void iHaveValidUserDataForAccountCreation() {
		logger.info("Generating valid data for account creation.");
		createAccountRequest = TestDataGenerator.generateValidCreateAccountRequest();
		Allure.addAttachment("Generated Account Request", createAccountRequest.toString());
	}

	@Step("Generating data with existing email for account creation")
	@Given("I have user data with existing email for account creation")
	public void iHaveUserDataWithExistingEmailForAccountCreation() {
		logger.info("Generating data with existing email for account creation.");
		createAccountRequest = TestDataGenerator.generateCreateAccountRequestWithExistingEmail();
		Allure.addAttachment("Generated Account Request with Existing Email", createAccountRequest.toString());
	}
}
