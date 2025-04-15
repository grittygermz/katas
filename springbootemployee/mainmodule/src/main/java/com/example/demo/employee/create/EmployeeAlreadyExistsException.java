package com.example.demo.employee.create;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String msg) {
        super(msg);
    }
}
