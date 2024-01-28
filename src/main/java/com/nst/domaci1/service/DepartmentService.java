package com.nst.domaci1.service;

import com.nst.domaci1.domain.Department;
import com.nst.domaci1.dto.DepartmentSetManagerDTO;

import java.util.List;

public interface DepartmentService {

    Department save(Department department) throws Exception;

    List<Department> getAll();

    void delete(String name) throws Exception;

    Department findById(String name) throws Exception;

    Department setManagerForDepartment(String departmentName, DepartmentSetManagerDTO dto) throws Exception;
}
