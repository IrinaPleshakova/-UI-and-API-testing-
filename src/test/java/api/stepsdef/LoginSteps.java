package api.stepsdef;

import api.models.VerifyLoginRequest;
import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;
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

		// Retrieve valid email and password from the configuration (or use generated data)
		String validEmail = TestDataGenerator.generateValidVerifyLoginRequestFromConfig().getEmail();
		String validPassword = TestDataGenerator.generateValidVerifyLoginRequestFromConfig().getPassword();

		createLoginRequest(validEmail, validPassword, "Valid");
	}

	@Step("Generating invalid login credentials")
	@Given("I have invalid login credentials")
	public void iHaveInvalidLoginCredentials() {
		logger.info("Generating invalid login credentials.");

		// Retrieve invalid email and password from the configuration
		String invalidEmail = TestDataGenerator.generateInvalidVerifyLoginRequest().getEmail();
		String invalidPassword = TestDataGenerator.generateInvalidVerifyLoginRequest().getPassword();

		createLoginRequest(invalidEmail, invalidPassword, "Invalid");
	}

	/**
	 * Helper method to create VerifyLoginRequest and log the request details.
	 */
	private void createLoginRequest(String email, String password, String requestType) {
		verifyLoginRequest = VerifyLoginRequest.builder()
				.email(email)
				.password(password)
				.build();

		logger.info("Generated {} login credentials: email={}, password={}", requestType, email, password);
		Allure.addAttachment(requestType + " Login Request", verifyLoginRequest.toString());
	}
}
