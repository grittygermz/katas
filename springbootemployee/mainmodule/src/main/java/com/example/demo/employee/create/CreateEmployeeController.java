package com.example.demo.employee.create;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/v1/api/employees")
public class CreateEmployeeController {

    private final CreateEmployeeService createEmployeeService;

    public CreateEmployeeController(CreateEmployeeService createEmployeeService) {
        this.createEmployeeService = createEmployeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addAnEmployee(@Valid @RequestBody AddEmployeePayload addEmployeePayload) {
        long employeeId = createEmployeeService.addEmployee(addEmployeePayload);

        //add header which provides link to perform a get request
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employeeId)
                .toUri();

        return ResponseEntity.created(location).build();
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictingException.class)
    public Map<String, String> handleConflictException(ConflictingException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return errors;
    }
}
