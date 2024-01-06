package com.nst.domaci1.converter.impl;

import com.nst.domaci1.converter.DtoEntityConverter;
import com.nst.domaci1.domain.AcademicTitleHistory;
import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import org.springframework.stereotype.Component;

@Component
public class AcademicTitleHistoryConverter implements DtoEntityConverter<AcademicTitleHistoryDTO, AcademicTitleHistory> {

    private MemberConverter memberConverter;
    private AcademicTitleConverter academicTitleConverter;
    private ScientificFieldConverter scientificFieldConverter;

    public AcademicTitleHistoryConverter(MemberConverter memberConverter, AcademicTitleConverter academicTitleConverter, ScientificFieldConverter scientificFieldConverter) {
        this.memberConverter = memberConverter;
        this.academicTitleConverter = academicTitleConverter;
        this.scientificFieldConverter = scientificFieldConverter;
    }

    @Override
    public AcademicTitleHistoryDTO toDTO(AcademicTitleHistory titleHistory) {
        return new AcademicTitleHistoryDTO(
                titleHistory.getStartDate(),
                titleHistory.getEndDate(),
                academicTitleConverter.toDTO(titleHistory.getAcademicTitle()),
                scientificFieldConverter.toDTO(titleHistory.getScientificField()),
                memberConverter.toDTO(titleHistory.getMember()));
    }

    @Override
    public AcademicTitleHistory toEntity(AcademicTitleHistoryDTO dto) {
        return new AcademicTitleHistory(
                dto.getStartDate(),
                dto.getEndDate(),
                academicTitleConverter.toEntity(dto.getAcademicTitle()),
                scientificFieldConverter.toEntity(dto.getScientificField()),
                memberConverter.toEntity(dto.getMemberDTO()));
    }
}
