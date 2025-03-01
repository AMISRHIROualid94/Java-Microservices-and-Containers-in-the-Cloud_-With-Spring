package ma.oualid.productserver.controller;

import ma.oualid.productserver.model.Product;
import lombok.RequiredArgsConstructor;
import ma.oualid.productserver.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;

    //------------------- Retreive a Product --------------------------------------------------------
    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {

        LOGGER.info("Start");
        LOGGER.debug("Fetching Product with id: {}", id);

        Product product = productRepository.findById(id).get();
        if (product == null) {
            LOGGER.debug("Product with id: {} not found", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    //------------------- Create a Product --------------------------------------------------------
    @PostMapping(value = "/products")
    public ResponseEntity<Product> postProduct(@RequestBody Product product) {

        LOGGER.info("Start");
        LOGGER.debug("Creating Product with code: {}", product.getCode());

        List<Product> products = productRepository.findByCode(product.getCode());
        if (products.size() > 0) {
            LOGGER.debug("A Product with code {} already exist", product.getCode());
            return new ResponseEntity<Product>(HttpStatus.CONFLICT);
        }

        Product newProduct = productRepository.save(product);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
    }

    //------------------- Update a Product --------------------------------------------------------
    @PutMapping(value = "/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {

        LOGGER.info("Start");
        LOGGER.debug("Updating Product with id: {}", id);

        Product currentProduct = productRepository.findById(id).get();

        if (currentProduct == null) {
            LOGGER.debug("Product with id: {} not found", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        currentProduct.setName(product.getName());
        currentProduct.setCode(product.getCode());
        currentProduct.setTitle(product.getTitle());
        currentProduct.setPrice(product.getPrice());

        Product newProduct = productRepository.save(currentProduct);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
    }

    //------------------- Retreive all Products --------------------------------------------------------
    @GetMapping(value = "/products",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getAllProducts() {

        LOGGER.info("Start");
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            LOGGER.debug("No products retreived from repository");
            return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
        }
        List<Product> list = new ArrayList<Product>();
        for(Product product:products){
            list.add(product);
        }
        list.forEach(item->LOGGER.debug(item.toString()));
        LOGGER.info("Ending");
        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    //------------------- Delete a Product --------------------------------------------------------
    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id) {

        LOGGER.info("Start");
        LOGGER.debug("Fetching & Deleting Product with id: {}", id);
        Product product = productRepository.findById(id).get();
        if (product == null) {
            LOGGER.debug("Product with id: {} not found, hence not deleted", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        productRepository.delete(product);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    //------------------- Delete All Products --------------------------------------------------------
    @DeleteMapping(value = "/products")
    public ResponseEntity<Product> deleteAllProducts() {

        LOGGER.info("Start");
        long count = productRepository.count();
        LOGGER.debug("Deleting {} products", count);
        productRepository.deleteAll();
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
}
