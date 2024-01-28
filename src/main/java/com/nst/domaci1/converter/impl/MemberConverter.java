package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.MemberDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements DtoEntityConverter<MemberDTO, Member> {

    private final DepartmentConverter departmentConverter;
    private AcademicTitleConverter academicTitleConverter;
    private EducationTitleConverter educationTitleConverter;
    private ScientificFieldConverter scientificFieldConverter;

    public MemberConverter(DepartmentConverter departmentConverter, AcademicTitleConverter academicTitleConverter, EducationTitleConverter educationTitleConverter, ScientificFieldConverter scientificFieldConverter) {
        this.departmentConverter = departmentConverter;
        this.academicTitleConverter = academicTitleConverter;
        this.educationTitleConverter = educationTitleConverter;
        this.scientificFieldConverter = scientificFieldConverter;
    }

    @Override
    public MemberDTO toDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                academicTitleConverter.toDTO(member.getAcademicTitle()),
                educationTitleConverter.toDTO(member.getEducationTitle()),
                scientificFieldConverter.toDTO(member.getScientificField()),
                departmentConverter.toDTO(member.getDepartment()));
    }

    @Override
    public Member toEntity(MemberDTO dto) {
        return new Member(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                academicTitleConverter.toEntity(dto.getAcademicTitle()),
                educationTitleConverter.toEntity(dto.getEducationTitle()),
                scientificFieldConverter.toEntity(dto.getScientificField()),
                departmentConverter.toEntity(dto.getDepartmentDTO()));
    }
}
