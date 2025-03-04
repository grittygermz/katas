package com.example.demo.exceptions;

public class InvalidException extends RuntimeException{
    public InvalidException(String message) {
        super(message);
    }
}
