package com.oualid.productweb.controller;

import com.oualid.productweb.db.InMemoryDB;
import com.oualid.productweb.model.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ProduitController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProduitController.class);

    private final InMemoryDB inMemoryDB;

    @GetMapping(value = "/productsweb",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getAllProducts() {
        LOGGER.info("Start");
        List<Product> products = inMemoryDB.getAllProducts();
        if(products.isEmpty()){
            LOGGER.debug("No products retreived from in-memory repository");
            return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
        }
        products.forEach(product -> LOGGER.debug(product.toString()));
        LOGGER.info("Ending");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/productsweb/{productId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        LOGGER.info("Start");
        LOGGER.debug("Fetching Product with id: {}", productId);
        Product product = inMemoryDB.getProduct(productId);
        if (product == null){
            LOGGER.debug("Product with id: {} not found in in-memory repository", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Ending");
        return new ResponseEntity<Product>(product,HttpStatus.OK);
    }

    @PostMapping(value = "/productsweb",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        LOGGER.info("Start");
        LOGGER.debug("Creating Product with id: {}", product.getProductId());

        Product productFound = inMemoryDB.getProduct(product.getProductId());
        if (null != productFound) {
            LOGGER.debug("A Product with code {} already exist", product.getCode());
            return new ResponseEntity<Product>(HttpStatus.CONFLICT);
        }

        inMemoryDB.addProduct(product);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @DeleteMapping(value = "/productsweb/{productId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> deleteProduct(@PathVariable("productId")String productId) {

        LOGGER.info("Start");
        LOGGER.debug("Fetching & Deleting Product with id: {}", productId);
        Product productFound = inMemoryDB.getProduct(productId);
        if (productFound == null) {
            LOGGER.debug("Product with id: {} not found, hence not deleted", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        inMemoryDB.deleteProduct(productId);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/productsweb/{productId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateProduct(@PathVariable("productId")String productId, @RequestBody Product product) {

        LOGGER.info("Start");
        LOGGER.debug("Updating Product with id: {}", productId);

        Product currentProduct = inMemoryDB.getProduct(productId);

        if (currentProduct == null) {
            LOGGER.debug("Product with id: {} not found", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        currentProduct.setName(product.getName());
        currentProduct.setCode(product.getCode());
        currentProduct.setTitle(product.getTitle());
        currentProduct.setPrice(product.getPrice());

        //We have already updated product in-memory by reference, still...
        Product newProduct = inMemoryDB.updateProduct(currentProduct);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
    }

    @PatchMapping(value = "/productsweb/{productId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> patchProduct(@PathVariable("productId")String productId, @RequestBody Product product) {

        LOGGER.info("Start");
        LOGGER.debug("Updating Price of Product with id: {}", productId);

        Product currentProduct = inMemoryDB.getProduct(productId);

        if (currentProduct == null) {
            LOGGER.debug("Product with id: {} not found", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        if(product.getName() != null){
            currentProduct.setName(product.getName());
        }
        if(product.getCode() != null){
            currentProduct.setCode(product.getCode());
        }
        if(product.getTitle() != null){
            currentProduct.setTitle(product.getTitle());
        }
        if(product.getPrice() > 0.0){
            currentProduct.setPrice(product.getPrice());
        }

        Product newProduct = inMemoryDB.updateProduct(currentProduct);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
    }
}
