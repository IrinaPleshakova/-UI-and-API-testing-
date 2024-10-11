package utils;

import api.models.CreateAccountRequest;
import api.models.VerifyLoginRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.pages.SignupPage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Utility class for generating test data with Allure reporting and Faker.
 */
public class TestDataGenerator {

	private static final Faker faker = new Faker();
	private static final Logger logger = LogManager.getLogger(TestDataGenerator.class);
	private static final String[] TITLES = {"Mr", "Ms"}; // Array for random title selection

	/**
	 * Generates a valid CreateAccountRequest and saves generated email and password
	 * into config_api.properties for future use.
	 */
	@Step("Generating valid CreateAccountRequest with Faker")
	public static CreateAccountRequest generateValidCreateAccountRequest() {
		String email = faker.internet().emailAddress();
		String password = faker.internet().password(8, 16);
		return buildCreateAccountRequest(email, password);
	}

	/**
	 * Generates a CreateAccountRequest with an existing email.
	 */
	@Step("Generating CreateAccountRequest with existing email")
	public static CreateAccountRequest generateCreateAccountRequestWithExistingEmail(String existingEmail) {
		String password = ConfigProvider.getExistingPassword();

		if (password == null || password.isEmpty()) {
			password = faker.internet().password(8, 16);
		}

		return buildCreateAccountRequest(existingEmail, password);
	}

	/**
	 * Generates a valid VerifyLoginRequest using credentials from config_api.properties.
	 */
	@Step("Generating valid VerifyLoginRequest from config_api.properties")
	public static VerifyLoginRequest generateValidVerifyLoginRequestFromConfig() {
		// Retrieve the valid email and password from config_api.properties
		String email = ConfigProvider.getValidEmail();
		String password = ConfigProvider.getValidPassword();

		// Log the email and password being used
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

	/**
	 * Builds CreateAccountRequest from existing user data (response from GET request).
	 */
	@Step("Building CreateAccountRequest from existing data")
	public static CreateAccountRequest buildCreateAccountRequestFromExistingData(Map<String, Object> userData) {
		return CreateAccountRequest.builder()
				.name((String) userData.getOrDefault("name", faker.name().fullName()))
				.email((String) userData.get("email"))
				.password(ConfigProvider.getExistingPassword())
				.title(getRandomTitle())
				.birth_date(getRandomDay())
				.birth_month(getRandomMonth())
				.birth_year(getRandomYear())
				.firstname((String) userData.getOrDefault("first_name", faker.name().firstName()))
				.lastname((String) userData.getOrDefault("last_name", faker.name().lastName()))
				.company((String) userData.getOrDefault("company", faker.company().name()))
				.address1((String) userData.getOrDefault("address1", faker.address().streetAddress()))
				.address2((String) userData.getOrDefault("address2", faker.address().secondaryAddress()))
				.country((String) userData.getOrDefault("country", "United States"))
				.zipcode((String) userData.getOrDefault("zipcode", faker.address().zipCode()))
				.state((String) userData.getOrDefault("state", faker.address().state()))
				.city((String) userData.getOrDefault("city", faker.address().city()))
				.mobile_number((String) userData.getOrDefault("mobile_number", faker.phoneNumber().cellPhone()))
				.build();
	}

	/**
	 * Helper method to build a CreateAccountRequest.
	 */
	@Step("Building CreateAccountRequest with email: {email}")
	private static CreateAccountRequest buildCreateAccountRequest(String email, String password) {
		LocalDate localDate = generateRandomBirthDate();
		String day = String.valueOf(localDate.getDayOfMonth());
		String month = String.valueOf(localDate.getMonthValue());
		String year = String.valueOf(localDate.getYear());

		return CreateAccountRequest.builder()
				.name(faker.name().fullName())
				.email(email)
				.password(password)
				.title(getRandomTitle())
				.birth_date(day)
				.birth_month(month)
				.birth_year(year)
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
	 * Generates a random title (either "Mr" or "Ms").
	 */
	private static String getRandomTitle() {
		Random random = new Random();
		return TITLES[random.nextInt(TITLES.length)];
	}

	/**
	 * Generates a random day (from 1 to 28 to avoid issues with month lengths).
	 */
	private static String getRandomDay() {
		return String.valueOf(faker.number().numberBetween(1, 28));
	}

	/**
	 * Generates a random month (from 1 to 12).
	 */
	private static String getRandomMonth() {
		return String.valueOf(faker.number().numberBetween(1, 12));
	}

	/**
	 * Generates a random year (from 1950 to 2003 for users aged between 18 and 65).
	 */
	private static String getRandomYear() {
		return String.valueOf(faker.number().numberBetween(1950, 2003));
	}

	/**
	 * Generates a random birth date.
	 */
	private static LocalDate generateRandomBirthDate() {
		Date randomDate = faker.date().birthday(18, 65);
		return randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Fills out the registration form using Faker-generated data.
	 */
	@Step("Filling out registration form with Faker data")
	public static void fillRegistrationForm(SignupPage signupPage) {
		String password = faker.internet().password();
		LocalDate localDate = generateRandomBirthDate();
		String day = String.valueOf(localDate.getDayOfMonth());
		String month = String.valueOf(localDate.getMonthValue());
		String year = String.valueOf(localDate.getYear());

		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String company = faker.company().name();
		String address1 = faker.address().streetAddress();
		String address2 = faker.address().secondaryAddress();
		String state = faker.address().state();
		String city = faker.address().city();
		String zipcode = faker.address().zipCode();
		String mobileNumber = faker.phoneNumber().cellPhone();

		signupPage.selectTitle(getRandomTitle());
		signupPage.enterPassword(password);
		signupPage.selectDateOfBirth(day, month, year);
		signupPage.enterFirstName(faker.name().firstName());
		signupPage.enterLastName(faker.name().lastName());
		signupPage.enterCompany(faker.company().name());
		signupPage.enterAddress1(faker.address().streetAddress());
		signupPage.enterAddress2(faker.address().secondaryAddress());
		signupPage.selectCountry("United States");
		signupPage.enterState(faker.address().state());
		signupPage.enterCity(faker.address().city());
		signupPage.enterZipcode(faker.address().zipCode());
		signupPage.enterMobileNumber(faker.phoneNumber().cellPhone());

//		Allure.addAttachment("Filled Registration Form", "User registration form filled with Faker-generated data.");
		Allure.addAttachment("Filled Registration Form",
				"Password: " + password + "\n" +
						"Date of Birth: " + day + "/" + month + "/" + year + "\n" +
						"First Name: " + firstName + "\n" +
						"Last Name: " + lastName + "\n" +
						"Company: " + company + "\n" +
						"Address1: " + address1 + "\n" +
						"Address2: " + address2 + "\n" +
						"State: " + state + "\n" +
						"City: " + city + "\n" +
						"Zipcode: " + zipcode + "\n" +
						"Mobile Number: " + mobileNumber
		);
	}

	/**
	 * Generates a random first name using Faker.
	 */
	public static String generateRandomFirstName() {
		return faker.name().firstName();
	}

	/**
	 * Generates a random email using Faker.
	 */
	public static String generateRandomEmail() {
		return faker.internet().emailAddress();
	}
}
