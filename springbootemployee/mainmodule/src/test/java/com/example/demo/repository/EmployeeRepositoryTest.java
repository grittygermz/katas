package com.example.demo.repository;

import com.example.demo.entity.EmployeeDao;
import com.example.demo.models.employee.EmployeeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    void shouldFindEmployeeWithId() {
        Optional<EmployeeDao> employeeDaoOptional = employeeRepository.findByEmployeeId(123);
        EmployeeDao employeeDao = employeeDaoOptional.get();

        assertThat(employeeDao).isNotNull();
        assertThat(employeeDao.getEmployeeId()).isEqualTo(123);
        assertThat(employeeDao.getEmployeeType()).isEqualTo(EmployeeType.FULLTIMEEMPLOYEE.getValue());
        assertThat(employeeDao.getBaseSalary().intValue()).isEqualTo(90000);
    }

    @Test
    void shouldCheckIfEmployeeWithEmployeeIdExists() {
        assertThat(employeeRepository.existsByEmployeeId(123)).isTrue();
    }

    @Test
    @Transactional
    void shouldDeleteExistingEmployeeById() {
        employeeRepository.deleteByEmployeeId(123L);
        assertThat(employeeRepository.existsByEmployeeId(123)).isFalse();
    }

}