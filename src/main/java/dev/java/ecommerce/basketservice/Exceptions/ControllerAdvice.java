package dev.java.ecommerce.basketservice.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDataNotFoundException(DataNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIllegalStateException(IllegalStateException ex) {
        return "Item não encontrado";
    }

    @ExceptionHandler(BunissesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBunissesException(BunissesException ex) {
        return ex.getMessage();
    }
}
