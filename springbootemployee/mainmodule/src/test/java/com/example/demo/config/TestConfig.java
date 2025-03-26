package com.example.demo.config;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.StockServiceClient;
import com.example.demo.update.UpdateEmployeeService;
import com.example.demo.utils.EmployeeUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public EmployeeUtils employeeUtils(StockServiceClient stockServiceClient) {
        return new EmployeeUtils(stockServiceClient);
    }

    //@Bean
    //public StockServiceClient stockServiceClient(RestClient restClient) {
    //    return new StockServiceClient(restClient);
    //}

    //@Bean
    //public RestClient restClient(){
    //    return RestClient.builder()
    //            .baseUrl("http://localhost:9001")
    //            .build();
    //}

    @Bean
    public UpdateEmployeeService updateEmployeeService(EmployeeRepository employeeRepository, EmployeeUtils employeeUtils) {
        return new UpdateEmployeeService(employeeRepository, employeeUtils);
    }
}
