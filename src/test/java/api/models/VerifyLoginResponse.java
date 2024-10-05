package api.models;

import lombok.Data;

/**
 * Model class representing a response from the verify login API.
 */
@Data
public class VerifyLoginResponse {
	private int responseCode;
	private String message;
}
