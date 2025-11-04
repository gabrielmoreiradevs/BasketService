package dev.java.ecommerce.basketservice.Exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.data.crossstore.ChangeSetPersister;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new DataNotFoundException("Product not found");
            default:
                return new Exception("Generic error");
        }
    }
}
