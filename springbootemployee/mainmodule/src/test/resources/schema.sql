DROP TABLE IF EXISTS EMPLOYEE;

CREATE TABLE Employee (
    Id BIGINT NOT NULL AUTO_INCREMENT,
    EmployeeId BIGINT UNIQUE,
    EmployeeType VARCHAR(255) NOT NULL,
    WorkingHoursPerDay INT,
    BaseSalary DOUBLE,
    PRIMARY KEY (Id)
);