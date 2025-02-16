package org.example;

public class NotInCartException extends Exception{
    public NotInCartException(String message) {
        super(message);
    }
}
