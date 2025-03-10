package ma.oualid.productserver.repo;

import ma.oualid.productserver.model.ProductCategoryOR;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "productcategorydata", path = "productcategorydata")
public interface ProductCategoryRepository extends CrudRepository<ProductCategoryOR, Long> {

	List<ProductCategoryOR> findByName(@Param("name") String  name);
}
