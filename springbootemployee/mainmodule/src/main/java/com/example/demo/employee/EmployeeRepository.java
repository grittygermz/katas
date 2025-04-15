package com.example.demo.employee;

import com.example.demo.employee.models.EmployeeDao;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeDao, Long> {
    Optional<EmployeeDao> findByEmployeeId(long employeeId);

    boolean existsByEmployeeId(long employeeId);

    // spring data jdbc lacking feature to create derived query for deletes
    @Query("delete from employee e where e.employeeId = :employeeId")
    @Modifying
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
