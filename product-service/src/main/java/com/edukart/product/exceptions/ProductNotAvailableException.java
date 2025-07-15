package com.edukart.product.exceptions;

public class ProductNotAvailableException extends RuntimeException{

    public ProductNotAvailableException(String message) {
        super(message);
    }

}
