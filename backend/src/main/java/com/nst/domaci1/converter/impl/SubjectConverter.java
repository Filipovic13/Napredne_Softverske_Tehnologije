package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.Subject;
import com.nst.domaci1.dto.SubjectDTO;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter implements DtoEntityConverter<SubjectDTO, Subject> {

    @Override
    public SubjectDTO toDTO(Subject subject) {
        return new SubjectDTO(
                subject.getId(),
                subject.getName(),
                subject.getEspb(),
                subject.getDepartment() == null ? null :
                        subject.getDepartment().getName());

    }

    @Override
    public Subject toEntity(SubjectDTO dto) {
        return new Subject(
                dto.getId(),
                dto.getName(),
                dto.getEspb(),
                Department.builder().name(dto.getDepartment()).build());
    }
}
