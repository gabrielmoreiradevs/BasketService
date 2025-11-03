package dev.java.ecommerce.basketservice.Service;

import dev.java.ecommerce.basketservice.Client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.Client.response.PlatziProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final PlatziStoreClient platziStoreClient;

    @Cacheable(value = "products")
    public List<PlatziProductResponse> getAllProducts() {
        log.info("Getting all products from Platzi Store API");
        return platziStoreClient.getAllProducts();
    }

    @Cacheable(value = "product", key = "#id")
    public PlatziProductResponse getProductById(Long id) {
        log.info("Getting product by id from Platzi Store API {}", id);
        return platziStoreClient.getProductById(id);

    }
}
