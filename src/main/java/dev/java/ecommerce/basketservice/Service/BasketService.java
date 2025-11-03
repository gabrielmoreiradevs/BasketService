package dev.java.ecommerce.basketservice.Service;

import dev.java.ecommerce.basketservice.Client.response.PlatziProductResponse;
import dev.java.ecommerce.basketservice.Controller.Request.BasketRequest;
import dev.java.ecommerce.basketservice.Controller.Request.PaymentRequest;
import dev.java.ecommerce.basketservice.Controller.Request.ProductRequest;
import dev.java.ecommerce.basketservice.Entity.Basket;
import dev.java.ecommerce.basketservice.Entity.Product;
import dev.java.ecommerce.basketservice.Entity.Status;
import dev.java.ecommerce.basketservice.Repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductService productService;

    public Basket getBasketById(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Basket with id " + id + " not found"));
    }

    public Basket payBasket(String basketId, PaymentRequest request) {
        Basket existingBasket = getBasketById(basketId);
        existingBasket.setPaymentMethod(request.getPaymentMethod());
        existingBasket.setStatus(Status.SOLD);
        return basketRepository.save(existingBasket);
    }

    public Basket createBasket(BasketRequest basketRequest) {
        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(existingBasket -> {
                    throw new IllegalStateException("Client already has an open basket with id: " + existingBasket.getId());
                });

        List<Product> products = new ArrayList<>();
        basketRequest.products().forEach(product -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(product.id());
            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(product.quantity())
                    .build());
        });

        Basket basket = Basket.builder()
                .client(basketRequest.clientId())
                .status(Status.OPEN)
                .products(products)
                .build();

        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }

    public Basket updateBasket(String id, BasketRequest basketRequest) {
        Basket existingBasket = getBasketById(id);

        if (existingBasket.getStatus() != Status.OPEN) {
            throw new IllegalStateException("Only open baskets can be updated");
        }

        List<Product> products = new ArrayList<>();
        basketRequest.products().forEach(product -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(product.id());
            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(product.quantity())
                    .build());
        });

        existingBasket.setProducts(products);
        existingBasket.calculateTotalPrice();
        return basketRepository.save(existingBasket);
    }
}
