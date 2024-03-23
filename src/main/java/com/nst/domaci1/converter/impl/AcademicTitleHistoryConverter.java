package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.AcademicTitle;
import com.nst.domaci1.domain.AcademicTitleHistory;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.domain.ScientificField;
import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import org.springframework.stereotype.Component;

@Component
public class AcademicTitleHistoryConverter implements DtoEntityConverter<AcademicTitleHistoryDTO, AcademicTitleHistory> {

    @Override
    public AcademicTitleHistoryDTO toDTO(AcademicTitleHistory titleHistory) {
        return titleHistory == null ? null : new AcademicTitleHistoryDTO(
                titleHistory.getId(),
                titleHistory.getStartDate(),
                titleHistory.getEndDate(),
                titleHistory.getAcademicTitle().getAcademicTitleName(),
                titleHistory.getScientificField().getScientificFieldName(),
                titleHistory.getMember().getId()
        );
    }

    @Override
    public AcademicTitleHistory toEntity(AcademicTitleHistoryDTO dto) {
        return dto == null ? null : new AcademicTitleHistory(
                dto.getId(),
                dto.getStartDate(),
                dto.getEndDate(),
                AcademicTitle.builder()
                        .academicTitleName(dto.getAcademicTitle())
                        .build(),
                ScientificField.builder()
                        .scientificFieldName(dto.getScientificField())
                        .build(),
                Member.builder().
                        id(dto.getMemberId()).build()
        );
    }
}
