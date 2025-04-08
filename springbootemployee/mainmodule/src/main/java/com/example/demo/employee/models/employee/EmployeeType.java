package com.example.demo.employee.models.employee;

public enum EmployeeType {
    FULLTIMEEMPLOYEE("FullTime"),
    PARTTIMEEMPLOYEE("PartTime"),
    CONTRACTOR("Contractor");

    private final String value;

    EmployeeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
