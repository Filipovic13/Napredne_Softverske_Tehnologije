package com.nst.domaci1.service;

import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import java.util.List;

public interface ManagementOfDepartmentHistoryService {


    ManagementOfDepartmentHistory setSupervisorForDepartment(Long memberId, String departmentName) throws Exception;

    ManagementOfDepartmentHistory setSecretaryForDepartment(Long memberId, String departmentName) throws Exception;

    List<ManagementOfDepartmentHistory> findAllManagersByDepartmentName(String departmentName) throws Exception;

    List<ManagementOfDepartmentHistory> findAllByMemberAndDepartment(Long memberId, String departmentName) throws Exception;

    void deleteManagementOfDepartmentHistory(Long managementOfDepartmentHistoryId) throws  Exception;

    ManagementOfDepartmentHistory findByIdManagementOfDepartmentHistory(Long managementOfDepartmentHistoryId) throws  Exception;

}
