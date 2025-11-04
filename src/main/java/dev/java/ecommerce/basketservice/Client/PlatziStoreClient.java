package dev.java.ecommerce.basketservice.Client;

import dev.java.ecommerce.basketservice.Client.response.PlatziProductResponse;
import dev.java.ecommerce.basketservice.Exceptions.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "PlatziStoreClient", url = "https://fakestoreapi.com/", configuration = {CustomErrorDecoder.class})
public interface PlatziStoreClient {

    @GetMapping("/products")
    List<PlatziProductResponse> getAllProducts();

    @GetMapping("/products/{id}")
    Optional<PlatziProductResponse> getProductById(@PathVariable Long id);
}
