package com.example.demo.employee.update;

import com.example.demo.employee.models.exchange.EmployeeDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("v1/api/employees")
public class UpdateEmployeeController {

    private final UpdateEmployeeService updateEmployeeService;

    public UpdateEmployeeController(UpdateEmployeeService updateEmployeeService) {
        this.updateEmployeeService = updateEmployeeService;
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO completelyUpdateAnEmployee(@PathVariable long id, @Valid @RequestBody PutEmployeePayload putEmployeePayload) {
        return updateEmployeeService.updateEmployee(id, putEmployeePayload);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO partiallyUpdateAnEmployee(@PathVariable long id, @Valid @RequestBody PatchEmployeePayload patchEmployeePayload) {
        return updateEmployeeService.updateEmployee(id, patchEmployeePayload);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            if(err instanceof FieldError) {
                String fieldName = ((FieldError)err).getField();
                String errorMessage = err.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            } else {
                errors.put(err.getObjectName(), err.getDefaultMessage());
            }
        });
        return errors;
    }
}
