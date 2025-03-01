package ma.oualid.productweb.controller;

import ma.oualid.productweb.client.ProductServiceProxy;
import ma.oualid.productweb.model.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableFeignClients(basePackageClasses = ProductServiceProxy.class)
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final RestTemplate restTemplate;

    @Value("${acme.PRODUCT_SERVICE_URL}")
    private String PRODUCT_SERVICE_URL;

    @GetMapping(value = "/productsweb",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getAllProducts() {
        LOGGER.info("Start");
        ParameterizedTypeReference<List<Product>> responseTypeRef = new ParameterizedTypeReference<List<Product>>() {};
        ResponseEntity<List<Product>> productList = restTemplate.exchange(PRODUCT_SERVICE_URL, HttpMethod.GET, null,responseTypeRef);
        LOGGER.info("Ending");
        return new ResponseEntity<>(productList.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/productsweb/{productId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        LOGGER.info("Start");
        LOGGER.debug("Fetching Product with id: {}", productId);
        Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL+"/"+productId,Product.class);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(product,HttpStatus.OK);
    }

    @PostMapping(value = "/productsweb",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        LOGGER.info("Start");
        LOGGER.debug("Creating Product with id: {}", product.getProductId());
        restTemplate.postForObject(PRODUCT_SERVICE_URL,product,Product.class);
        LOGGER.info("Ending");
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @DeleteMapping(value = "/productsweb/{productId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteProduct(@PathVariable("productId")String productId) {
        LOGGER.info("Start");
        LOGGER.debug("Fetching & Deleting Product with id: {}", productId);
        restTemplate.delete(PRODUCT_SERVICE_URL+"/"+productId);
        LOGGER.info("Ending");
    }

    @PutMapping(value = "/productsweb/{productId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateProduct(@PathVariable("productId")String productId, @RequestBody Product product) {
        LOGGER.info("Start");
        String uri = PRODUCT_SERVICE_URL + "/" + productId;
        LOGGER.debug("Attempting to update Product with ID : {} ...", productId);
        restTemplate.put(uri, product, Product.class);
        LOGGER.debug("Product with ID : {} updated. Retreiving updated product back...", productId);
        Product updatedProduct = restTemplate.getForObject(uri, Product.class);
        LOGGER.info("Ending...");
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }

}
