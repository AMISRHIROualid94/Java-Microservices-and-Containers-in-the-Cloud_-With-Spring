package ma.oualid.productserver.mapper;

import ma.oualid.productserver.model.ProductCategory;
import ma.oualid.productserver.model.ProductCategoryOR;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory entityToApi(ProductCategoryOR entity);
    ProductCategoryOR apiToEntity(ProductCategory api);
}
