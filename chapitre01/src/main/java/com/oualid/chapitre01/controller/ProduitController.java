package com.oualid.chapitre01.controller;

import com.oualid.chapitre01.db.InMemoryDB;
import com.oualid.chapitre01.model.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/produit")
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
}
