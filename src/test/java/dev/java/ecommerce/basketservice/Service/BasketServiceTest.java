package dev.java.ecommerce.basketservice.Service;

import dev.java.ecommerce.basketservice.Controller.Request.BasketRequest;
import dev.java.ecommerce.basketservice.Entity.Basket;
import dev.java.ecommerce.basketservice.Entity.Status;
import dev.java.ecommerce.basketservice.Repository.BasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @InjectMocks
    BasketService basketService;

    @Mock
    BasketRepository basketRepository;
    ProductService productService;

    Basket basket;

    @BeforeEach
    public void setup() {
        basket = new Basket("basketId123", "clientId123", 10.2, null, Status.OPEN);
    }

    @Test
    void deveBuscarBasketPorIdComSucesso(){
        when(basketRepository.findById(basket.getId())).thenReturn(Optional.of(basket));

        Basket baskets = basketService.getBasketById(basket.getId());

        assertEquals(basket, baskets);
        verify(basketRepository).findById(basket.getId());
        verifyNoMoreInteractions(basketRepository);
    }

    @Test
    void deveCriarBasketComSucesso() {
        BasketRequest basketRequest = new BasketRequest(123L, new ArrayList<>());
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);

        Basket createdBasket = basketService.createBasket(basketRequest);

        assertEquals(basket.getClient(), createdBasket.getClient());
        verify(basketRepository).save(any(Basket.class));
    }
}
