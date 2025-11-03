package dev.java.ecommerce.basketservice.Entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
