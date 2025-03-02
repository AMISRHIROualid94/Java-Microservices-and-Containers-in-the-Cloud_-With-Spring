package ma.oualid.productserver.repo;

import ma.oualid.productserver.model.Product;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class ProductRestRepositoryConfig implements RepositoryRestConfigurer {

    //expose ids in the http request and response
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){
        config.exposeIdsFor(Product.class);
    }
}
