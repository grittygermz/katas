package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Payload> getPayload() {
        return ResponseEntity.ok(new Payload(20));
    }
}
