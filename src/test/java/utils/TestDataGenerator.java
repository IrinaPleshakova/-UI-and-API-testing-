package utils;

import api.models.CreateAccountRequest;
import api.models.VerifyLoginRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for generating test data with Allure reporting.
 */
public class TestDataGenerator {

	private static final Faker faker = new Faker();
	private static final Logger logger = LogManager.getLogger(TestDataGenerator.class);

	/**
	 * Generates a valid CreateAccountRequest and saves generated email and password
	 * into config_api.properties for future use.
	 */
	@Step("Generating valid CreateAccountRequest with Faker")
	public static CreateAccountRequest generateValidCreateAccountRequest() {
		// Generating email and password with Faker
		String email = faker.internet().emailAddress();
		String password = faker.internet().password(8, 16);
		logger.info("Generated email: " + email);
		logger.info("Generated password: " + password);

		// Saving the generated data in config_api.properties
		ConfigProvider.setGeneratedEmail(email);
		ConfigProvider.setGeneratedPassword(password);

		return buildCreateAccountRequest(email, password);
	}

	/**
	 * Generates a CreateAccountRequest with an existing email.
	 */
	@Step("Generating CreateAccountRequest with an existing email")
	public static CreateAccountRequest generateCreateAccountRequestWithExistingEmail() {
		// Use existing email from configuration file
		return buildCreateAccountRequest(ConfigProvider.getValidEmail(), "Password123!");
	}

	/**
	 * Helper method to build a CreateAccountRequest.
	 */
	@Step("Building CreateAccountRequest with email: {email}")
	private static CreateAccountRequest buildCreateAccountRequest(String email, String password) {
		return CreateAccountRequest.builder()
				.name(faker.name().fullName())
				.email(email)
				.password(password)
				.title("Mr")
				.birth_date("01")
				.birth_month("01")
				.birth_year("1990")
				.firstname(faker.name().firstName())
				.lastname(faker.name().lastName())
				.company(faker.company().name())
				.address1(faker.address().streetAddress())
				.address2(faker.address().secondaryAddress())
				.country("United States")
				.zipcode(faker.address().zipCode())
				.state(faker.address().state())
				.city(faker.address().city())
				.mobile_number(faker.phoneNumber().cellPhone())
				.build();
	}

	/**
	 * Generates a valid VerifyLoginRequest using newly generated email and password.
	 */
	@Step("Generating valid VerifyLoginRequest with Faker")
	public static VerifyLoginRequest generateValidVerifyLoginRequestWithGeneratedData() {
		String generatedEmail = faker.internet().emailAddress();
		String generatedPassword = faker.internet().password(8, 16);

		logger.info("Generated email: " + generatedEmail);
		logger.info("Generated password: " + generatedPassword);

		// Optionally save the generated email and password to config if needed
		ConfigProvider.setGeneratedEmail(generatedEmail);
		ConfigProvider.setGeneratedPassword(generatedPassword);

		return VerifyLoginRequest.builder()
				.email(generatedEmail) // Use the generated email
				.password(generatedPassword) // Use the generated password
				.build();
	}

	/**
	 * Generates a valid VerifyLoginRequest using credentials from config_api.properties
	 */
	@Step("Generating valid VerifyLoginRequest from config_api.properties")
	public static VerifyLoginRequest generateValidVerifyLoginRequestFromConfig() {
		// Retrieve the valid email and password from config_api.properties
		String email = ConfigProvider.getValidEmail();
		String password = ConfigProvider.getValidPassword();

		logger.info("Using email from config: " + email);
		logger.info("Using password from config: " + password);

		return VerifyLoginRequest.builder()
				.email(email) // Use the valid email from config
				.password(password) // Use the valid password from config
				.build();
	}

	/**
	 * Generates an invalid VerifyLoginRequest using invalid credentials from config_api.properties.
	 */
	@Step("Generating invalid VerifyLoginRequest")
	public static VerifyLoginRequest generateInvalidVerifyLoginRequest() {
		return VerifyLoginRequest.builder()
				.email(ConfigProvider.getInvalidEmail()) // Use invalid email
				.password(ConfigProvider.getInvalidPassword()) // Use invalid password
				.build();
	}
}
