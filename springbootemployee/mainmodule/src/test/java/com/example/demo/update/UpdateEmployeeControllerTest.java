package com.example.demo.update;

import com.example.demo.domain.EmployeeDTO;
import com.example.demo.models.employee.EmployeeType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(UpdateEmployeeController.class)
class UpdateEmployeeControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    UpdateEmployeeService updateEmployeeService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldCompletelyUpdateAnEmployeeByPutRequest() throws JsonProcessingException {
        EmployeeDTO employeeDTO = new EmployeeDTO(
                1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50001"),
                new BigDecimal("50001"),
                0);
        when(updateEmployeeService.updateEmployee(anyLong(), any(PutEmployeePayload.class)))
                .thenReturn(employeeDTO);
        MvcTestResult result = mockMvcTester.put().uri("/v1/api/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeType": "Contractor",
                            "baseSalary": 50001
                        }
                        """)
                .exchange();

        assertThat(result).hasStatusOk()
                .bodyJson()
                .isEqualTo(objectMapper.writeValueAsString(employeeDTO));
    }

    @Test
    @DisplayName("for invalid contractor payload")
    void shouldFailPartiallyUpdateAnEmployeeByPutRequest() throws JsonProcessingException {
        MvcTestResult result = mockMvcTester.put().uri("/v1/api/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeType": "Contractor"
                        }
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"baseSalary\":\"fullTime or contractor employee should have value for baseSalary\",\"putEmployeePayload\":\"invalid put employee payload\"}");
    }

    @Test
    @DisplayName("for invalid part time employee payload")
    void shouldFailPartiallyUpdateAnEmployeeByPutRequest1() throws JsonProcessingException {
        MvcTestResult result = mockMvcTester.put().uri("/v1/api/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeType": "PartTime"
                        }
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"workingHoursPerDay\":\"partTime employee should have value for workingHoursPerDay\",\"putEmployeePayload\":\"invalid put employee payload\"}");
    }

    @Test
    void shouldPartiallyUpdateAnEmployeeByPatchRequest() throws JsonProcessingException {
        EmployeeDTO employeeDTO = new EmployeeDTO(
                1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50001"),
                new BigDecimal("50001"),
                0);
        when(updateEmployeeService.updateEmployee(anyLong(), any(PatchEmployeePayload.class)))
                .thenReturn(employeeDTO);
        MvcTestResult result = mockMvcTester.patch().uri("/v1/api/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeType": "Contractor"
                        }
                        """)
                .exchange();

        assertThat(result).hasStatusOk()
                .bodyJson()
                .isEqualTo(objectMapper.writeValueAsString(employeeDTO));
    }

    @Test
    void shouldFailPatchIfInvalidPayload() throws JsonProcessingException {
        MvcTestResult result = mockMvcTester.patch().uri("/v1/api/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "baseSalary": 0.001
                        }
                        """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"baseSalary\":\"must be greater than or equal to 0.01\"}");
    }
}