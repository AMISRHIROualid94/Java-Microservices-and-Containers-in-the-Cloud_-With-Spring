package ma.oualid.productweb.controller;

import ma.oualid.productweb.client.ProductServiceProxy;
import ma.oualid.productweb.model.Product;
import lombok.RequiredArgsConstructor;
import ma.oualid.productweb.service.ProductServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableFeignClients(basePackageClasses = ProductServiceProxy.class)
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductServicePort{
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductServiceProxy productServiceProxy;


    @GetMapping(value = "/productsweb",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getAllProducts() {
        LOGGER.info("Delegating to product service proxy GET");
        return productServiceProxy.getAllProducts();
    }

    @GetMapping(value = "/productsweb/{productId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        LOGGER.info("Delegating to product service proxy GET");
        return productServiceProxy.getProduct(productId);
    }

    @PostMapping(value = "/productsweb",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        LOGGER.info("Delegating to product service proxy POST");
        return productServiceProxy.addProduct(product);
    }

    @DeleteMapping(value = "/productsweb/{productId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> deleteProduct(@PathVariable("productId")String productId) {
        LOGGER.info("Delegating to product service proxy DELETE");
        return productServiceProxy.deleteProduct(productId);
    }

    @PutMapping(value = "/productsweb/{productId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateProduct(@PathVariable("productId")String productId, @RequestBody Product product) {
        LOGGER.info("Delegating to product service proxy PUT");
        return productServiceProxy.updateProduct(productId, product);
    }

}
