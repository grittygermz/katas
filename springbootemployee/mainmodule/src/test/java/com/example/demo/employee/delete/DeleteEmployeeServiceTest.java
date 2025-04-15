package com.example.demo.employee.delete;

import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteEmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    DeleteEmployeeService deleteEmployeeService;

    @Test
    void shouldDeleteEmployeeIfExisting() {
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        deleteEmployeeService.deleteEmployee(1L);

        verify(employeeRepository).deleteByEmployeeId(anyLong());
    }

    @Test
    void shouldThrowExceptionIfNotExisting() {
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> deleteEmployeeService.deleteEmployee(1L)).isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("employee 1 to delete does not exist");
    }
}