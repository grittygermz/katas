package com.example.demo.employee.create;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CreateEmployeeController.class)
class CreateEmployeeControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private CreateEmployeeService createEmployeeService;

    @Test
    void shouldAddEmployee() {
        when(createEmployeeService.addEmployee(any(CreateEmployeePayload.class))).thenReturn(5L);

        MvcTestResult result = mockMvcTester.post().uri("/v1/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeId": 5,
                            "employeeType": "FullTime",
                            "baseSalary": 120000
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.CREATED)
                .hasRedirectedUrl("http://localhost/v1/api/employees/5");
    }

    @Test
    void shouldHave400ErrorWithMissingField() {
        MvcTestResult result = mockMvcTester.post().uri("/v1/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeType": "FullTime",
                            "baseSalary": 120000
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"employeeId\":\"employeeId is mandatory\"}");
    }

    @Test
    @DisplayName("baseSalary should be more than 0 for fulltime or contractor employees")
    void shouldHave400ErrorWithInvalidCombination() {
        MvcTestResult result = mockMvcTester.post().uri("/v1/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeType": "FullTime",
                            "baseSalary": 0
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"createEmployeePayload\":\"Invalid create employee payload\",\"baseSalary\":\"Base salary must be more than 0 for FullTime and Contractors\",\"employeeId\":\"employeeId is mandatory\"}");
    }

    @Test
    @DisplayName("workinghoursperday should not be provided for fulltime or contractor employees")
    void shouldHave400ErrorWithInvalidCombination1() {
        MvcTestResult result = mockMvcTester.post().uri("/v1/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeId": 5,
                            "employeeType": "Contractor",
                            "baseSalary": 9000,
                            "workingHoursPerDay": 2
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"createEmployeePayload\":\"Invalid create employee payload\",\"workingHoursPerDay\":\"workingHoursPerDay must not be provided for FullTime and Contractors\"}");
    }

    @Test
    @DisplayName("basesalary should not be provided for parttime employees")
    void shouldHave400ErrorWithInvalidCombination2() {
        MvcTestResult result = mockMvcTester.post().uri("/v1/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeId": 5,
                            "employeeType": "PartTime",
                            "baseSalary": 9000,
                            "workingHoursPerDay": 1
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"createEmployeePayload\":\"Invalid create employee payload\",\"baseSalary\":\"base salary must not be provided for PartTime\"}");
    }

    @Test
    @DisplayName("workinghoursperday should be more than 0 for parttime employees")
    void shouldHave400ErrorWithInvalidCombination3() {
        MvcTestResult result = mockMvcTester.post().uri("/v1/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeId": 5,
                            "employeeType": "PartTime",
                            "workingHoursPerDay": 0
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"createEmployeePayload\":\"Invalid create employee payload\",\"workingHoursPerDay\":\"workingHoursPerDay must be more than 0 for PartTime\"}");
    }

    @Test
    void shouldHave409ErrorIfEmployeeIdIsUsed() {
        when(createEmployeeService.addEmployee(any(CreateEmployeePayload.class)))
                .thenThrow(new EmployeeAlreadyExistsException("employee with id 1 already exists"));
        MvcTestResult result = mockMvcTester.post().uri("/v1/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "employeeId": 1,
                            "employeeType": "PartTime",
                            "workingHoursPerDay": 5
                        }
                        """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.CONFLICT)
                .bodyJson().isEqualTo("{\"message\": \"employee with id 1 already exists\"}");
    }



}