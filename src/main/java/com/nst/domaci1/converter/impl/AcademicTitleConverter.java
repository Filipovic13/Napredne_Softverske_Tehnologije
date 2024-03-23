package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.AcademicTitle;
import com.nst.domaci1.dto.AcademicTitleDTO;
import org.springframework.stereotype.Component;

@Component
public class AcademicTitleConverter implements DtoEntityConverter<AcademicTitleDTO, AcademicTitle> {

    @Override
    public AcademicTitleDTO toDTO(AcademicTitle academicTitle) {
        return academicTitle == null ? null : new AcademicTitleDTO
                (academicTitle.getAcademicTitleCode(),
                        academicTitle.getAcademicTitleName()
                );
    }

    @Override
    public AcademicTitle toEntity(AcademicTitleDTO academicTitleDTO) {
        return academicTitleDTO == null ? null : new AcademicTitle(
                academicTitleDTO.getCode(), academicTitleDTO.getName()
        );
    }
}
