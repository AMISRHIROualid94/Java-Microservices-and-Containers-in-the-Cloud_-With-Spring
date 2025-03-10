package ma.oualid.productserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product{

	private String productId;
	private String name;
	private String code;
	private String title;
	private Double price;
	private String category;
}
