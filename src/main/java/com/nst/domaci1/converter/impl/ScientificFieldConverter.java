package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.ScientificField;
import com.nst.domaci1.dto.ScientificFieldDTO;
import org.springframework.stereotype.Component;

@Component
public class ScientificFieldConverter implements DtoEntityConverter<ScientificFieldDTO, ScientificField> {
    @Override
    public ScientificFieldDTO toDTO(ScientificField scientificField) {
        return new ScientificFieldDTO(scientificField.getScientificFieldCode(), scientificField.getScientificFieldName());
    }

    @Override
    public ScientificField toEntity(ScientificFieldDTO scientificFieldDTO) {
        return new ScientificField(scientificFieldDTO.getCode(), scientificFieldDTO.getName());
    }
}
