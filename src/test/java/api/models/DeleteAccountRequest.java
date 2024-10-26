package api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteAccountRequest {
	private String email;
	private String password;
}
