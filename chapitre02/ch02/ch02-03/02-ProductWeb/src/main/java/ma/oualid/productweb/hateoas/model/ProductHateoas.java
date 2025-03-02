package ma.oualid.productweb.hateoas.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ProductHateoas extends RepresentationModel<ProductHateoas> {

    private String productId;
    private String name;
    private String code;;
    private String title;
    private Double price;
}
