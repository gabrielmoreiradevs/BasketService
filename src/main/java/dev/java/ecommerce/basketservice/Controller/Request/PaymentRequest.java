package dev.java.ecommerce.basketservice.Controller.Request;

import dev.java.ecommerce.basketservice.Entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private PaymentMethod paymentMethod;

}
