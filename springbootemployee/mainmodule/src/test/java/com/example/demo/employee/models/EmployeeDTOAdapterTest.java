package com.example.demo.employee.models;

import com.example.demo.employee.models.exchange.EmployeeDTO;
import com.example.demo.employee.models.exchange.EmployeeDTOAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.example.demo.employee.helpers.DummyObjectFactory.getEmployeeDTOWithType;
import static com.example.demo.employee.helpers.DummyObjectFactory.getEmployeeWithType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
class EmployeeDTOAdapterTest {

    @Autowired
    EmployeeDTOAdapter employeeDTOAdapter;

    @Test
    void shouldConvertFullTimeEmployeeToEmployeeDTO() {
        EmployeeDTO employeeDTO = employeeDTOAdapter.getEmployeeDTO(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE));
        assertThat(employeeDTO).isEqualTo(getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE));
    }

    @Test
    void shouldConvertContractorEmployeeToEmployeeDTO() {
        EmployeeDTO employeeDTO =employeeDTOAdapter.getEmployeeDTO(getEmployeeWithType(EmployeeType.CONTRACTOR));
        assertThat(employeeDTO).isEqualTo(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));
    }

    @Test
    void shouldConvertPartTimeEmployeeToEmployeeDTO() {
        EmployeeDTO employeeDTO = employeeDTOAdapter.getEmployeeDTO(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE));
        assertThat(employeeDTO).isEqualTo(getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE));
    }
}