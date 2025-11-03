package dev.java.ecommerce.basketservice.Controller.Request;

import lombok.Builder;

public record ProductRequest(Long id, Integer quantity) {
}
