package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.AcademicTitle;
import com.nst.domaci1.dto.AcademicTitleDTO;
import org.springframework.stereotype.Component;

@Component
public class AcademicTitleConverter implements DtoEntityConverter<AcademicTitleDTO, AcademicTitle> {
    @Override
    public AcademicTitleDTO toDTO(AcademicTitle academicTitle) {
        return new AcademicTitleDTO(academicTitle.getAcademicTitleCode(), academicTitle.getAcademicTitleName());
    }

    @Override
    public AcademicTitle toEntity(AcademicTitleDTO academicTitleDTO) {
        return new AcademicTitle(academicTitleDTO.getCode(), academicTitleDTO.getName());
    }
}
