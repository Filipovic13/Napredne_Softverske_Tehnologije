package com.nst.domaci1.service;

import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.dto.DepartmentSetManagerDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO save(DepartmentDTO department) throws Exception;

    List<DepartmentDTO> getAll();

    void delete(String name) throws Exception;

    DepartmentDTO findById(String name) throws Exception;

    DepartmentDTO setManagerForDepartment(String departmentName, DepartmentSetManagerDTO dto) throws Exception;
}
