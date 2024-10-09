package api.stepsdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.ConfigProvider;

import static api.stepsdef.CommonSteps.response;

/**
 * Step definition class for getting user details by email with Allure reporting.
 */
public class UserDetailByEmailSteps {
	private static final Logger logger = LogManager.getLogger(UserDetailByEmailSteps.class);
	public static String email;

	@Step("Setting valid email for user detail retrieval")
	@Given("I have a valid email for getting user detail")
	public void iHaveAValidEmailForGettingUserDetail() {
		logger.info("Setting valid email for user detail retrieval.");
		email = ConfigProvider.getValidEmail();
		Allure.addAttachment("Valid Email", email);
	}

	@Step("Setting invalid email for user detail retrieval")
	@Given("I have an invalid email for getting user detail")
	public void iHaveAnInvalidEmailForGettingUserDetail() {
		logger.info("Setting invalid email for user detail retrieval.");
		email = ConfigProvider.getInvalidEmail();
		Allure.addAttachment("Invalid Email", email);
	}

	@Step("Asserting the response contains user details and attaching the full response")
	@Then("the response message should contain user details")
	public void theResponseMessageShouldContainUserDetails() {
		logger.info("Attaching full response message to Allure.");

		// Convert the whole answer into a string and attach it to Allure
		String responseBody = response.asString();
		Allure.addAttachment("Full Response Body", responseBody);

		// Select key fields for validation
		Integer userId = response.jsonPath().getInt("user.id");
		String userName = response.jsonPath().getString("user.name");
		String userEmail = response.jsonPath().getString("user.email");

		logger.info("User details in response: ID = {}, Name = {}, Email = {}", userId, userName, userEmail);

		Assert.assertNotNull(userId, "User ID should not be null");
		Assert.assertNotNull(userName, "User Name should not be null");
		Assert.assertNotNull(userEmail, "User Email should not be null");
	}
}