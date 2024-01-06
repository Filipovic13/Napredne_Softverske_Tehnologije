package com.nst.domaci1.service;

import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagementOfDepartmentHistoryID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ManagementOfDepartmentHistoryService {

    ManagementOfDepartmentHistory save(ManagementOfDepartmentHistory managementHistory) throws Exception;

    List<ManagementOfDepartmentHistory> getAll();

    Page<ManagementOfDepartmentHistory> getAll(Pageable pageable);

    void delete(ManagementOfDepartmentHistoryID id) throws Exception;

    ManagementOfDepartmentHistory update(ManagementOfDepartmentHistory managementHistory) throws Exception;

    ManagementOfDepartmentHistory findById(ManagementOfDepartmentHistoryID id) throws Exception;

    List<ManagementOfDepartmentHistory> findAllByDepartmentName(String departmentName) throws Exception;
}
