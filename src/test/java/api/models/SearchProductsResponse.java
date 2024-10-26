package api.models;

import lombok.Data;
import java.util.List;

/**
 * Model class representing the response from the Search Product API.
 */
@Data
public class SearchProductsResponse {
	private int responseCode;
	private List<Product> products;

	@Data
	public static class Product {
		private int id;
		private String name;
		private String price;
		private String brand;
	}
}
