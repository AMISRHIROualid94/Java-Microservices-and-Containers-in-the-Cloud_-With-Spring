package ma.oualid.productweb.client;

import ma.oualid.productweb.service.ProductServicePort;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service",url = "http://localhost:8081")
public interface ProductServiceProxy extends ProductServicePort {
}
