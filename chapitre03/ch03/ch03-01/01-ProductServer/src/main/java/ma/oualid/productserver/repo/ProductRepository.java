package ma.oualid.productserver.repo;

import ma.oualid.productserver.model.ProductOR;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "productdata", path = "productdata")
public interface ProductRepository extends CrudRepository<ProductOR, Long> {

	List<ProductOR> findByCode(@Param("code") String  code);

	List<ProductOR> findByCategory(@Param("category") String  category);
}
