package com.example.demo.employee.create;

import com.example.demo.employee.EmployeeDao;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.models.employee.EmployeeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CreateEmployeeService createEmployeeService;

    @Test
    void shouldNotAddEmployee() {
        AddEmployeePayload addEmployeePayload = new AddEmployeePayload(1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("10000"));
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);

        assertThatThrownBy(() -> createEmployeeService.addEmployee(addEmployeePayload))
                .isInstanceOf(ConflictingException.class)
                .hasMessageContaining("employee with id 1 already exists");
    }

    @Test
    void shouldAddEmployee() {
        AddEmployeePayload addEmployeePayload = new AddEmployeePayload(1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("10000"));
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(false);
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(
                new EmployeeDao(1L,
                        EmployeeType.CONTRACTOR.getValue(),
                        null,
                        new BigDecimal("10000")));

        assertThat(createEmployeeService.addEmployee(addEmployeePayload)).isEqualTo(1L);
    }

}