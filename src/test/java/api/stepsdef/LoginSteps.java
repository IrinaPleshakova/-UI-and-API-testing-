package api.stepsdef;

import api.models.VerifyLoginRequest;
import io.cucumber.java.en.Given;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TestDataGenerator;

/**
 * Step definition class for login verification API steps.
 */
public class LoginSteps {
	public static VerifyLoginRequest verifyLoginRequest;
	private static final Logger logger = LogManager.getLogger(LoginSteps.class);

	@Step("Generating valid login credentials")
	@Given("I have valid login credentials")
	public void iHaveValidLoginCredentials() {
		logger.info("Generating valid login credentials.");
		verifyLoginRequest = TestDataGenerator.generateValidVerifyLoginRequest();
	}

	@Step("Generating invalid login credentials")
	@Given("I have invalid login credentials")
	public void iHaveInvalidLoginCredentials() {
		logger.info("Generating invalid login credentials.");
		verifyLoginRequest = TestDataGenerator.generateInvalidVerifyLoginRequest();
	}
}
