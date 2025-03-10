package ma.oualid.productserver;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ma.oualid.productserver.model.ProductCategoryOR;
import ma.oualid.productserver.model.ProductOR;
import ma.oualid.productserver.repo.ProductCategoryRepository;
import ma.oualid.productserver.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    private void init(){

        logger.info("Start...");

        //Deleting all existing data on start.....

        logger.debug("Deleting all existing data on start...");
        productCategoryRepository.deleteAll();
        productRepository.deleteAll();


        logger.debug("Creating initial data on start...");

        ProductCategoryOR productCategoryOR = new ProductCategoryOR();
        productCategoryOR.setName("Mobile");
        productCategoryOR.setDescription("Mobile phones");
        productCategoryOR.setTitle("Mobiles and Tablet");
        productCategoryOR.setImgUrl("mobile.jpg");
        productCategoryRepository.save(productCategoryOR);

        productCategoryOR = new ProductCategoryOR();
        productCategoryOR.setName("Tablet");
        productCategoryOR.setDescription("Tablet pads");
        productCategoryOR.setTitle("Tablet like pads");
        productCategoryOR.setImgUrl("tablet.jpg");
        productCategoryRepository.save(productCategoryOR);

        ProductOR productOR = new ProductOR();
        productOR.setName("Kamsung Mobile");
        productOR.setCode("KAMSUNG-TRIOS");
        productOR.setTitle("Tablet Trios 12 inch , black , 12 px ....");
        productOR.setPrice(12000.00D);
        productOR.setCategory("Mobile");
        productRepository.save(productOR);

        productOR = new ProductOR();
        productOR.setName("Lokia Mobile");
        productOR.setCode("LOKIA-POMIA");
        productOR.setTitle("Lokia 12 inch , white , 14px ....");
        productOR.setPrice(9000.00D);
        productOR.setCategory("Mobile");
        productRepository.save(productOR);

        productOR = new ProductOR();
        productOR.setName("Mapple Mobile");
        productOR.setCode("MAPPLE-EPHONE");
        productOR.setTitle("Mapple 7 inch, purple, 14px ....");
        productOR.setPrice(8000.00D);
        productOR.setCategory("Mobile");
        productRepository.save(productOR);

        productOR = new ProductOR();
        productOR.setName("Mapple Tablet");
        productOR.setCode("MAPPLE-PAD");
        productOR.setTitle("Mapple 11 inch , grey, 140px ....");
        productOR.setPrice(19000.00D);
        productOR.setCategory("Tablet");
        productRepository.save(productOR);

        //TODO: Add rest of products and catagories...........
        logger.info("End");
    }
}
