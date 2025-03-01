package ma.oualid.productserver.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {
    @Id
    private String productId;
    private String name;
    private String code;;
    private String title;
    private Double price;


}
