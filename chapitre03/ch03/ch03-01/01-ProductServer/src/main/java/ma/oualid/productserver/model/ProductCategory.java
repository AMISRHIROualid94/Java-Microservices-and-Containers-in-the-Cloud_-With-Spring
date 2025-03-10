package ma.oualid.productserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

	private String id;
	private String name;
	private String title;
	private String description;
	private String imgUrl;
}