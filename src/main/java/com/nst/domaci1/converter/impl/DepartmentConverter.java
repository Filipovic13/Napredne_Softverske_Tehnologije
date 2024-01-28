package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.dto.DepartmentDTO;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter implements DtoEntityConverter<DepartmentDTO, Department> {

    @Override
    public DepartmentDTO toDTO(Department d) {
        return new DepartmentDTO(
                d.getName(),
                d.getShortName(),
                d.getSupervisorId(),
                d.getSecretaryId()
        );
    }

    @Override
    public Department toEntity(DepartmentDTO dto) {
        return new Department(
                dto.getName(),
                dto.getShortName(),
                dto.getSupervisorId(),
                dto.getSecretaryId()
        );
    }
}
