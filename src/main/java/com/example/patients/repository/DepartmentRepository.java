package com.example.patients.repository;

import com.example.patients.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT dep.* " +
            "FROM department dep " +
            "WHERE binary dep.name = :name", nativeQuery = true)
    Department findDepartmentByName(String name);
}
