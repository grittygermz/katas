package com.example.demo.repository;

import com.example.demo.entity.EmployeeDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeDao, Long> {
    Optional<EmployeeDao> findByEmployeeId(long employeeId);

    boolean existsByEmployeeId(long employeeId);

    //doesnt seem to work..
    //void deleteByEmployeeId(long employeeId);
}
