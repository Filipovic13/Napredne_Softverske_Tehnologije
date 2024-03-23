package com.nst.domaci1.service;

import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;

import java.util.List;

public interface ManagementOfDepartmentHistoryService {

    List<ManagementOfDepartmentHistoryDTO> findAllByDepartmentName(String departmentName) throws Exception;

    List<ManagementOfDepartmentHistoryDTO> findAllByMember(Long memberId) throws Exception;

    void deleteById(Long managementOfDepartmentHistoryId) throws Exception;

    ManagementOfDepartmentHistoryDTO findById(Long managementOfDepartmentHistoryId) throws Exception;

    ManagementOfDepartmentHistoryDTO getLatestMangerOfDepartment(String departmentName, String managerRole) throws Exception;

    ManagementOfDepartmentHistoryDTO save (ManagementOfDepartmentHistoryDTO dto) throws Exception;

    ManagementOfDepartmentHistoryDTO update(Long managementOfDepartmentHistoryId, ManagementOfDepartmentHistoryDTO dto) throws Exception;
}
