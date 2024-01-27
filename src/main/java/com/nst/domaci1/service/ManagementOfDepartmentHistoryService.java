package com.nst.domaci1.service;

import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.dto.ManagementOfDepartmentHistorySaveUpdateDTO;

import java.util.List;

public interface ManagementOfDepartmentHistoryService {

    List<ManagementOfDepartmentHistory> findAllByDepartmentName(String departmentName) throws Exception;

    List<ManagementOfDepartmentHistory> findAllByMember(Long memberId) throws Exception;

    void deleteById(Long managementOfDepartmentHistoryId) throws Exception;

    ManagementOfDepartmentHistory findById(Long managementOfDepartmentHistoryId) throws Exception;

    ManagementOfDepartmentHistory getLatestMangerOfDepartment(String departmentName, String managerRole) throws Exception;

    ManagementOfDepartmentHistory save (ManagementOfDepartmentHistorySaveUpdateDTO dto) throws Exception;

    ManagementOfDepartmentHistory update(Long managementOfDepartmentHistoryId, ManagementOfDepartmentHistorySaveUpdateDTO dto) throws Exception;
}
