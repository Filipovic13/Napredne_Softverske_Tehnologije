package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.EducationTitle;
import com.nst.domaci1.dto.EducationTitleDTO;
import org.springframework.stereotype.Component;

@Component
public class EducationTitleConverter implements DtoEntityConverter<EducationTitleDTO, EducationTitle> {
    @Override
    public EducationTitleDTO toDTO(EducationTitle educationTitle) {
        return new EducationTitleDTO(educationTitle.getEducationTitleCode(), educationTitle.getEducationTitleName());
    }

    @Override
    public EducationTitle toEntity(EducationTitleDTO educationTitleDTO) {
        return new EducationTitle(educationTitleDTO.getCode(), educationTitleDTO.getName());
    }
}
