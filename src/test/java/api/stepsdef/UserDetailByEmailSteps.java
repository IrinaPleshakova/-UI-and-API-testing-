package api.stepsdef;

import io.cucumber.java.en.Given;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigProvider;

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
	}

	@Step("Setting invalid email for user detail retrieval")
	@Given("I have an invalid email for getting user detail")
	public void iHaveAnInvalidEmailForGettingUserDetail() {
		logger.info("Setting invalid email for user detail retrieval.");
		email = ConfigProvider.getInvalidEmail();
	}
}
