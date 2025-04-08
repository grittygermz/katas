package com.example.demo.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJdbcTest(properties = "spring.jpa.properties.hibernate.show_sql=true")
@Sql(scripts = "/schema.sql")
@Sql(scripts = "/data.sql")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @Transactional
    void shouldDeleteExistingEmployeeById() {
        employeeRepository.deleteByEmployeeId(123L);
        assertThat(employeeRepository.existsByEmployeeId(123)).isFalse();
    }

}