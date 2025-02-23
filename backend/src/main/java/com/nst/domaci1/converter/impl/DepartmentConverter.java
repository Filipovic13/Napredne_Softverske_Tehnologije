package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.DepartmentDTO;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter implements DtoEntityConverter<DepartmentDTO, Department> {

    @Override
    public DepartmentDTO toDTO(Department d) {
        return d == null ? null : new DepartmentDTO(
                d.getName(),
                d.getShortName(),
                d.getSupervisor().getId(),
                d.getSecretary().getId()
        );
    }

    @Override
    public Department toEntity(DepartmentDTO dto) {
        return dto == null ? null : new Department(
                dto.getName(),
                dto.getShortName(),
                Member.builder().id(dto.getSupervisorId()).build(),
                Member.builder().id(dto.getSecretaryId()).build()
        );
    }
}
