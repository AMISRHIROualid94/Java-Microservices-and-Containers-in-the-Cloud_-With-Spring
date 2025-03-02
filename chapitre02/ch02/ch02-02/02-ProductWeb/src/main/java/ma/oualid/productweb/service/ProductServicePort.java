package ma.oualid.productweb.service;

import ma.oualid.productweb.model.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductServicePort {

    @GetMapping(value = "/products",produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<Product>> getAllProducts();

    @GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> getProduct(@PathVariable("productId") String productId);

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> addProduct(@RequestBody Product product);

    @DeleteMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> deleteProduct(@PathVariable("productId") String productId);

    @PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> updateProduct(@PathVariable("id") String productId, @RequestBody Product product);

}
