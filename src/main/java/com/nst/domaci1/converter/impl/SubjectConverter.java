package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.Subject;
import com.nst.domaci1.dto.SubjectDTO;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter implements DtoEntityConverter<SubjectDTO, Subject> {

    private DepartmentConverter departmentConverter;

    public SubjectConverter(DepartmentConverter departmentConverter) {
        this.departmentConverter = departmentConverter;
    }

    @Override
    public SubjectDTO toDTO(Subject subject) {
        return new SubjectDTO(
                subject.getId(),
                subject.getName(),
                subject.getEspb(),
                departmentConverter.toDTO(subject.getDepartment()));
    }

    @Override
    public Subject toEntity(SubjectDTO dto) {
        return new Subject(
                dto.getId(),
                dto.getName(),
                dto.getEspb(),
                departmentConverter.toEntity(dto.getDepartmentDTO()));
    }
}
