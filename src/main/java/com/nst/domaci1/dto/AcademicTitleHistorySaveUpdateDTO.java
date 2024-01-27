package com.nst.domaci1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public class AcademicTitleHistorySaveUpdateDTO implements Serializable {

    @NotNull(message = "Start date must be entered!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date must be entered!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Academic title must be entered!")
    private String academicTitle;

    @NotBlank(message = "Scientific field must be entered!")
    private String scientificField;

    @NotNull(message = "Member ID must be entered!")
    private Long memberId;


    public AcademicTitleHistorySaveUpdateDTO() {
    }

    public AcademicTitleHistorySaveUpdateDTO(LocalDate startDate, LocalDate endDate, String academicTitle, String scientificField, Long memberId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicTitle = academicTitle;
        this.scientificField = scientificField;
        this.memberId = memberId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getScientificField() {
        return scientificField;
    }

    public void setScientificField(String scientificField) {
        this.scientificField = scientificField;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
