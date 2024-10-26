package api.models;

import lombok.Builder;
import lombok.Data;

/**
 * Model class representing a request to get user detail by email API.
 */
@Data
@Builder
public class UserDetailByEmailRequest {
	private String email;
}