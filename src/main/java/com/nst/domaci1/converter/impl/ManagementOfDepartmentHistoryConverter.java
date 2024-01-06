package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import org.springframework.stereotype.Component;

@Component
public class ManagementOfDepartmentHistoryConverter implements DtoEntityConverter<ManagementOfDepartmentHistoryDTO, ManagementOfDepartmentHistory> {

    private MemberConverter memberConverter;
    private DepartmentConverter departmentConverter;

    public ManagementOfDepartmentHistoryConverter(MemberConverter memberConverter, DepartmentConverter departmentConverter) {
        this.memberConverter = memberConverter;
        this.departmentConverter = departmentConverter;
    }

    @Override
    public ManagementOfDepartmentHistoryDTO toDTO(ManagementOfDepartmentHistory managementHistory) {
        return new ManagementOfDepartmentHistoryDTO(
                managementHistory.getStartDate(),
                managementHistory.getEndDate(),
                managementHistory.getSupervisorFirstName(),
                managementHistory.getSupervisorLastName(),
                managementHistory.getSecretaryFirstName(),
                managementHistory.getSecretaryLastName(),
                departmentConverter.toDTO(managementHistory.getDepartment()));
    }

    @Override
    public ManagementOfDepartmentHistory toEntity(ManagementOfDepartmentHistoryDTO dto) {
        return new ManagementOfDepartmentHistory(
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getSupervisorFirstName(),
                dto.getSupervisorLastName(),
                dto.getSecretaryFirstName(),
                dto.getSecretaryLastName(),
                departmentConverter.toEntity(dto.getDepartmentDTO()));
    }
}
