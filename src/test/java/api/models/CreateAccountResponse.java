package api.models;

import lombok.Data;

/**
 * Model class representing a response from the create account API.
 */
@Data
public class CreateAccountResponse {
	private int responseCode;
	private String message;
}
