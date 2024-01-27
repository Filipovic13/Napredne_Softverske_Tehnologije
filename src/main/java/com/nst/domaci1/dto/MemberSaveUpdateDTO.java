package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class MemberSaveUpdateDTO implements Serializable {

    @NotBlank(message = "Firstname must be entered!")
    private String firstName;

    @NotBlank(message = "Lastname must be entered!")
    private String lastName;

    @NotBlank(message = "Academic title must be entered!")
    private String academicTitle;

    @NotBlank(message = "Education title must be entered!")
    private String educationTitle;

    @NotBlank(message = "Scientific field  must be entered!")
    private String scientificField;

    @NotBlank(message = "Department name must be entered!")
    private String departmentName;

    public MemberSaveUpdateDTO() {
    }


    public MemberSaveUpdateDTO(String firstName, String lastName, String academicTitle, String educationTitle, String scientificField, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.academicTitle = academicTitle;
        this.educationTitle = educationTitle;
        this.scientificField = scientificField;
        this.departmentName = departmentName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getScientificField() {
        return scientificField;
    }

    public void setScientificField(String scientificField) {
        this.scientificField = scientificField;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
