package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class MemberChangeAllScienceFieldsDTO implements Serializable {

    @NotBlank(message = "Academic title must be entered!")
    private String academicTitle;

    @NotBlank(message = "Education title must be entered!")
    private String educationTitle;

    @NotBlank(message = "Science field must be entered!")
    private String scienceField;

    public MemberChangeAllScienceFieldsDTO() {
    }

    public MemberChangeAllScienceFieldsDTO(String academicTitle, String educationTitle, String scienceField) {
        this.academicTitle = academicTitle;
        this.educationTitle = educationTitle;
        this.scienceField = scienceField;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(String educationTitle) {
        this.educationTitle = educationTitle;
    }

    public String getScienceField() {
        return scienceField;
    }

    public void setScienceField(String scienceField) {
        this.scienceField = scienceField;
    }
}
