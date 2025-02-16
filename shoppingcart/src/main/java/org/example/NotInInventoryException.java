package org.example;

public class NotInInventoryException extends Exception{
    public NotInInventoryException(String message) {
        super(message);
    }
}
