package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.*;
import com.nst.domaci1.dto.MemberDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements DtoEntityConverter<MemberDTO, Member> {

    @Override
    public MemberDTO toDTO(Member member) {
        return member == null ? null : new MemberDTO(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getAcademicTitle() == null ? null :
                        member.getAcademicTitle().getAcademicTitleName(),
                member.getEducationTitle() == null ? null :
                        member.getEducationTitle().getEducationTitleCode(),
                member.getScientificField() == null ? null :
                        member.getScientificField().getScientificFieldName(),
                member.getDepartment() == null ? null :
                        member.getDepartment().getName()
        );
    }

    @Override
    public Member toEntity(MemberDTO dto) {
        return dto == null ? null : new Member(
                null,
                dto.getFirstName(),
                dto.getLastName(),
                AcademicTitle.builder()
                        .academicTitleName(dto.getAcademicTitle())
                        .build(),
                EducationTitle.builder()
                        .educationTitleName(dto.getEducationTitle())
                        .build(),
                ScientificField.builder()
                        .scientificFieldName(dto.getScientificField())
                        .build(),
                Department.builder()
                        .name(dto.getDepartmentName())
                        .build()
        );

    }
}
