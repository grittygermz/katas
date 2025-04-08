package com.example.demo.employee.delete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/v1/api/employees")
public class DeleteEmployeeController {

    private final DeleteEmployeeService deleteEmployeeService;

    public DeleteEmployeeController(DeleteEmployeeService deleteEmployeeService) {
        this.deleteEmployeeService = deleteEmployeeService;
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> deleteAnEmployee(@PathVariable long id) {
        deleteEmployeeService.deleteEmployee(id);
        return Map.of("id", String.valueOf(id), "message", "employee was successfully deleted");
    }
}
