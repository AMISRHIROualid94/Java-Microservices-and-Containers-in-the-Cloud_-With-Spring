package ma.oualid.productserver;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ma.oualid.productserver.model.Product;
import ma.oualid.productserver.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(InitApplication.class);
    private final ProductRepository productRepository;

    @PostConstruct
    public void initDb(){
        LOGGER.info("Start");

        //Deleting all existing data on start.....

        productRepository.deleteAll();


        Product product=new Product();
        product.setName("Kamsung D3");
        product.setCode("KAMSUNG-TRIOS");
        product.setTitle("Kamsung Trios 12 inch , black , 12 px ....");
        product.setPrice(12000.00);
        productRepository.save(product);

        product=new Product();
        product.setName("Lokia Pomia");
        product.setCode("LOKIA-POMIA");
        product.setTitle("Lokia 12 inch , white , 14px ....");
        product.setPrice(9000.00);
        productRepository.save(product);

        //TODO: Add rest of products and catagories...........
        LOGGER.info("End");
    }
}
