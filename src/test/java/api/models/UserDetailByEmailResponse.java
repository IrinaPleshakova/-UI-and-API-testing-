package api.models;

import lombok.Data;

/**
 * Model class representing a response from the get user detail by email API.
 */
@Data
public class UserDetailByEmailResponse {
	private int responseCode;
	private User user;  // Nested User object for user data

	@Data
	public class User {
		private int id;
		private String name;
		private String email;
		private String title;
		private String birth_day;
		private String birth_month;
		private String birth_year;
		private String first_name;
		private String last_name;
		private String company;
		private String address1;
		private String address2;
		private String country;
		private String state;
		private String city;
		private String zipcode;
	}
}