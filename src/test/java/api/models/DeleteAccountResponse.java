package api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteAccountResponse {
	private int responseCode;
	private String responseMessage;
}
