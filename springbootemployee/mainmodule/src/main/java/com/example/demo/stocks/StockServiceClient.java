package com.example.demo.stocks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class StockServiceClient {

    //private RestClient getRestClient() {
    //    return RestClient.builder()
    //            .baseUrl("http://localhost:9001")
    //            .build();
    //}
    private final RestClient restClient;

    //public StockServiceClient(RestClient restClient) {
    //    this.restClient = restClient;
    //}
    public StockServiceClient(@Autowired RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:9001").build();
    }

    private ResponseEntity<SamplePayload> makeExternalCallToStockService() {
        try {
            ResponseEntity<SamplePayload> result = restClient.get()
                    .uri("/")
                    .retrieve()
                    .toEntity(SamplePayload.class);
            log.info("{}", result.getBody());
            return result;
        } catch (RestClientException ex) {
            log.error("Server error occurred: " + ex.getMessage());
        }
        return null;
    }

    public SamplePayload retrieveStockCountFromService() {
        ResponseEntity<SamplePayload> stockCountResponse = makeExternalCallToStockService();
        if(stockCountResponse != null) {
            return stockCountResponse.getBody();
        }
        throw new RuntimeException("failure in call to stockcount service");
    }
}
