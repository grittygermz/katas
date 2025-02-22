package org.example;

public class InsufficientValueException extends RuntimeException{
    public InsufficientValueException(String message) {
        super(message);
    }
}
