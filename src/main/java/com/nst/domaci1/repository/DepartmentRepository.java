package com.nst.domaci1.repository;

import com.nst.domaci1.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT d.name FROM Department d")
    List<String> findAllNames();
}

