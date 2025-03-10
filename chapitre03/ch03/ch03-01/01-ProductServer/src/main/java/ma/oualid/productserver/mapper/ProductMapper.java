package ma.oualid.productserver.mapper;

import ma.oualid.productserver.model.Product;
import ma.oualid.productserver.model.ProductOR;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product entityToApi(ProductOR entity);
    ProductOR apiToEntity(Product api);
}
