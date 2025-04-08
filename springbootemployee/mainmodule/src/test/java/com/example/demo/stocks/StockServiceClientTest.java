package com.example.demo.stocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

//@Import(RestClientConfig.class)
@RestClientTest(StockServiceClient.class)
class StockServiceClientTest {

    @Autowired
    StockServiceClient stockServiceClient;

    @Autowired
    MockRestServiceServer mockServer;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldRetrieveStocksFromService() throws JsonProcessingException {
        String mockResponse = objectMapper.writeValueAsString(new SamplePayload(20));
        mockServer.expect(requestTo("http://localhost:9001/"))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        SamplePayload samplePayload = stockServiceClient.retrieveStockCountFromService();

        assertThat(samplePayload).isEqualTo(new SamplePayload(20));
        mockServer.verify();
    }

    @Test
    void shouldNotRetrieveStocksFromService() throws JsonProcessingException {
        mockServer.expect(requestTo("http://localhost:9001/"))
                .andRespond(withServerError());

        assertThatThrownBy(() -> stockServiceClient.retrieveStockCountFromService())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("failure in call to stockcount service");
        mockServer.verify();
    }

    @Test
    void shouldNotRetrieveStocksFromService1() throws JsonProcessingException {
        mockServer.expect(requestTo("http://localhost:9001/"))
                .andRespond(withBadRequest());

        assertThatThrownBy(() -> stockServiceClient.retrieveStockCountFromService())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("failure in call to stockcount service");
        mockServer.verify();
    }


}