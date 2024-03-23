package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagerRole;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import org.springframework.stereotype.Component;

@Component
public class ManagementOfDepartmentHistoryConverter implements DtoEntityConverter<ManagementOfDepartmentHistoryDTO, ManagementOfDepartmentHistory> {

    @Override
    public ManagementOfDepartmentHistoryDTO toDTO(ManagementOfDepartmentHistory managementHistory) {
        return managementHistory == null ? null : new ManagementOfDepartmentHistoryDTO(
                managementHistory.getId(),
                managementHistory.getStartDate(),
                managementHistory.getEndDate(),
                managementHistory.getManagerRole() == null ? null :
                        managementHistory.getManagerRole().toString(),
                managementHistory.getMember() == null ? null :
                        managementHistory.getMember().getId(),
                managementHistory.getDepartment() == null ? null :
                        managementHistory.getDepartment().getName()
        );
    }

    @Override
    public ManagementOfDepartmentHistory toEntity(ManagementOfDepartmentHistoryDTO dto) {
        return dto == null ? null : new ManagementOfDepartmentHistory(
                dto.getId(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getManagerRole().equalsIgnoreCase("supervisor") ? ManagerRole.SUPERVISOR : ManagerRole.SECRETARY,
                Member.builder().
                        id(dto.getId()).build(),
                Department.builder().
                        name(dto.getDepartmentName()).build()
        );
    }
}
