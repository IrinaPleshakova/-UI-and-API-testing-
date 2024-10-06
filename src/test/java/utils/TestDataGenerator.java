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
	@Step("Generating valid CreateAccountRequest")
	public static CreateAccountRequest generateValidCreateAccountRequest() {
		// Генерация email и пароля с помощью Faker
		String email = faker.internet().emailAddress();
		String password = faker.internet().password(8, 16);
		logger.info("Generated email: " + email);
		logger.info("Generated password: " + password);

		// Сохранение сгенерированных данных в config_api.properties
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
	 * Generates a valid VerifyLoginRequest using previously generated credentials
	 */
	@Step("Generating valid VerifyLoginRequest")
	public static VerifyLoginRequest generateValidVerifyLoginRequest() {
		return VerifyLoginRequest.builder()
				.email(ConfigProvider.getGeneratedEmail()) // Use the generated email
				.password(ConfigProvider.getGeneratedPassword()) // Use the generated password
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