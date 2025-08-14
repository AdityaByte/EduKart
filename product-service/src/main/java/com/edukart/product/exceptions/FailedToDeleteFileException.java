package com.edukart.product.exceptions;

public class FailedToDeleteFileException extends RuntimeException{
    public FailedToDeleteFileException(String message) {
        super(message);
    }
}
