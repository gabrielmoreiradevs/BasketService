package dev.java.ecommerce.basketservice.Service;

import dev.java.ecommerce.basketservice.Client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.Client.response.PlatziProductResponse;
import dev.java.ecommerce.basketservice.Entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private PlatziStoreClient platziStoreClient;

    private PlatziProductResponse productResponse;

    @BeforeEach
    public void setup() {
        productResponse = new PlatziProductResponse(1L, "Test Product", BigDecimal.valueOf(100.99));
    }

    @Test
    void deveBuscarTodosOsProdutosComSucesso() {
        when(platziStoreClient.getAllProducts()).thenReturn(Arrays.asList(productResponse));

        List<PlatziProductResponse> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals(productResponse, products.get(0));
        verify(platziStoreClient).getAllProducts();
        verifyNoMoreInteractions(platziStoreClient);
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        when(platziStoreClient.getProductById(1L)).thenReturn(Optional.of(productResponse));

        PlatziProductResponse product = productService.getProductById(1L);

        assertEquals(productResponse, product);
        verify(platziStoreClient).getProductById(1L);
        verifyNoMoreInteractions(platziStoreClient);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoForEncontrado() {
        when(platziStoreClient.getProductById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            productService.getProductById(1L);
        });

        verify(platziStoreClient).getProductById(1L);
        verifyNoMoreInteractions(platziStoreClient);
    }
}

