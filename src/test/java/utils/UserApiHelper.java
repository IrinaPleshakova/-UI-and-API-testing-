package utils;

import api.clients.AccountApiClient;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class UserApiHelper {

	private static final AccountApiClient client = new AccountApiClient();
	private static final Logger logger = LogManager.getLogger(UserApiHelper.class);

	/**
	 * Checks if a user with the given email exists via API.
	 * @return true if the user exists, false if not.
	 */
	public static boolean doesUserExist(String email) {
		Response response = client.getUserDetailByEmail(email);
		boolean userExists = response.getStatusCode() == 200 && response.jsonPath().get("user") != null;

		if (!userExists) {
			logger.info("User with email {} does not exist. Please create the user.", email);
		}

		return userExists;
	}

	/**
	 * Retrieves user data by email.
	 * @return User data as a map.
	 */
	public static Map<String, Object> getUserDataByEmail(String email) {
		Response response = client.getUserDetailByEmail(email);
		return response.jsonPath().getMap("user");
	}
}
