package com.example.demo.delete;

import com.example.demo.exceptions.InvalidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(DeleteEmployeeController.class)
class DeleteEmployeeControllerTest {

    @MockitoBean
    DeleteEmployeeService deleteEmployeeService;

    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void shouldDeleteEmployeeIfExists() {
        doNothing().when(deleteEmployeeService).deleteEmployee(anyLong());

        MvcTestResult result = mockMvcTester.delete().uri("/v1/api/employees/{id}", 1)
                .exchange();
        assertThat(result).hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("message", path -> assertThat(path).asString().isEqualTo("employee was successfully deleted"))
                .hasPathSatisfying("id", path -> assertThat(path).asString().isEqualTo("1"));
    }

    @Test
    void shouldHaveErrorIfNotExists() {
        doThrow(new InvalidException("employee 1 to delete does not exist")).when(deleteEmployeeService)
                .deleteEmployee(anyLong());

        MvcTestResult result = mockMvcTester.delete().uri("/v1/api/employees/{id}", 1)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isEqualTo("{\"message\":\"employee 1 to delete does not exist\"}");
    }
}