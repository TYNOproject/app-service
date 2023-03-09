package com.appservice.all.Postgres;

import com.appservice.all.Entities.Course;
import com.appservice.all.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = "SELECT * from departments", nativeQuery = true)
    List<Department> findAllDepartments();
}
