package com.example.demo.service;

import com.example.demo.domain.SamplePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class StockServiceClient {

    private final RestClient restClient;

    public StockServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<SamplePayload> getStockCount() {
        ResponseEntity<SamplePayload> result = restClient.get()
                .uri("/")
                .retrieve()
                .toEntity(SamplePayload.class);
        log.info("{}", result.getBody());
        return result;
    }
}
