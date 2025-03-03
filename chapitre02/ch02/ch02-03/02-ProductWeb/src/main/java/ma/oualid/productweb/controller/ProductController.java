package ma.oualid.productweb.controller;

import ma.oualid.productweb.hateoas.model.ProductHateoas;
import ma.oualid.productweb.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Value("${acme.PRODUCT_SERVICE_URL}")
    private String PRODUCT_SERVICE_URL;

    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CollectionModel<ProductHateoas>> getAllProducts() {

        LOGGER.info("Delegating to product µ service GET");
        ParameterizedTypeReference<List<Product>> responseTypeRef = new ParameterizedTypeReference<List<Product>>() {} ;
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(PRODUCT_SERVICE_URL, HttpMethod.GET, (HttpEntity<Product>) null, responseTypeRef);

        Link links[] = { linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("getAllProducts") };

        List<ProductHateoas> list = new ArrayList<ProductHateoas>();
        for (Product product : responseEntity.getBody()) {
            ProductHateoas productHateoas = convertEntityToHateoasEntity(product);
            list.add(productHateoas.add(linkTo(methodOn(ProductController.class).getProduct(productHateoas.getProductId())).withSelfRel()));
        }
        list.forEach(item -> LOGGER.debug(item.toString()));
        CollectionModel<ProductHateoas> result = CollectionModel.of(list, links);
        return new ResponseEntity<CollectionModel<ProductHateoas>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductHateoas> getProduct(@PathVariable("productId") String productId) {

        LOGGER.info("Delegating to product µ service GET");
        String uri = PRODUCT_SERVICE_URL + "/" + productId;
        Product productRetreived = restTemplate.getForObject(uri, Product.class);
        ProductHateoas productHateoas = convertEntityToHateoasEntity(productRetreived);
        productHateoas.add(linkTo(methodOn(ProductController.class).getProduct(productHateoas.getProductId())).withSelfRel());
        return new ResponseEntity<ProductHateoas>(productHateoas, HttpStatus.OK);

    }

    @RequestMapping(value = "/productsweb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductHateoas> addProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {

        LOGGER.info("Delegating  to product µ service POST");
        Product productRecieved= restTemplate.postForObject( PRODUCT_SERVICE_URL, product, Product.class);
        ProductHateoas productHateoas = convertEntityToHateoasEntity(productRecieved);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/products/{id}").buildAndExpand(product.getProductId()).toUri());
        productHateoas.add(linkTo(methodOn(ProductController.class).getProduct(productHateoas.getProductId())).withSelfRel());
        return new ResponseEntity<ProductHateoas>(productHateoas, HttpStatus.OK);

    }

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductHateoas> deleteProduct(@PathVariable("productId")String productId) {
        LOGGER.info("Delegating  to product µ service DELETE");
        restTemplate.delete(PRODUCT_SERVICE_URL + "/" +productId);
        return new ResponseEntity<ProductHateoas>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductHateoas> updateProduct(@PathVariable("productId")String productId,@RequestBody Product product) {

        LOGGER.info("Delegating  to product µ service PUT");
        restTemplate.put( PRODUCT_SERVICE_URL + "/" + productId, product, Product.class);
        ProductHateoas productHateoas = convertEntityToHateoasEntity(product);
        productHateoas.add(linkTo(methodOn(ProductController.class).getProduct(productHateoas.getProductId())).withSelfRel());
        return new ResponseEntity<ProductHateoas>(productHateoas, HttpStatus.OK);
    }

    private ProductHateoas convertEntityToHateoasEntity(Product product){
        return  modelMapper.map(product,  ProductHateoas.class);
    }

}
