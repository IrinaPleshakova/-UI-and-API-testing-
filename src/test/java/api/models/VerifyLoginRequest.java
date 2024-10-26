package api.models;

import lombok.Builder;
import lombok.Data;

/**
 * Model class representing a request to verify login.
 */
@Data
@Builder
public class VerifyLoginRequest {
	private String email;
	private String password;
}
