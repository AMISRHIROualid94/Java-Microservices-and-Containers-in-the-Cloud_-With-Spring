package com.oualid.chapitre01.db;

import com.oualid.chapitre01.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryDB {
    private final Logger LOGGER = LoggerFactory.getLogger(InMemoryDB.class);

    private Map<Long, Product> productDB;

    public InMemoryDB() {
        initDB();
    }

    public List<Product> getAllProducts() {

        return new ArrayList<Product>(productDB.values());
    }

    public Product getProduct(String productId) {

        return productDB.get(Long.parseLong(productId));
    }

    public void addProduct(Product product) {

        productDB.put(Long.parseLong(product.getProductId()), product);
    }

    public Product deleteProduct(String productId) {

        return productDB.remove(Long.parseLong(productId));
    }

    public Product updateProduct(Product product) {

        return productDB.put(Long.parseLong(product.getProductId()), product);
    }

    private void initDB(){

        LOGGER.info("Start");

        productDB = new HashMap<Long, Product>();

        Product product1 = new Product();
        product1.setProductId("1");
        product1.setName("Kamsung D3");
        product1.setCode("KAMSUNG-TRIOS");
        product1.setTitle("Kamsung Trios 12 inch , black , 12 px ....");
        product1.setPrice(12000.00);
        productDB.put(Long.parseLong(product1.getProductId()), product1);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setName("Lokia Pomia");
        product2.setCode("LOKIA-POMIA");
        product2.setTitle("Lokia 12 inch , white , 14px ....");
        product2.setPrice(9000.00);
        productDB.put(Long.parseLong(product2.getProductId()), product2);

        //TODO: Add rest of products and catagories...........
        LOGGER.info("Ending.");
    }
}
