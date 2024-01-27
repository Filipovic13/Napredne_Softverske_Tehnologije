package com.nst.domaci1.dto;


import java.io.Serializable;

public class MemberDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private AcademicTitleDTO academicTitle;

    private EducationTitleDTO educationTitle;

    private ScientificFieldDTO scientificField;

    private DepartmentDTO departmentDTO;

    public MemberDTO() {
    }

    public MemberDTO(Long id, String firstName, String lastName, AcademicTitleDTO academicTitle, EducationTitleDTO educationTitle, ScientificFieldDTO scientificField, DepartmentDTO departmentDTO) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.academicTitle = academicTitle;
        this.educationTitle = educationTitle;
        this.scientificField = scientificField;
        this.departmentDTO = departmentDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AcademicTitleDTO getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitleDTO academicTitle) {
        this.academicTitle = academicTitle;
    }

    public EducationTitleDTO getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(EducationTitleDTO educationTitle) {
        this.educationTitle = educationTitle;
    }

    public ScientificFieldDTO getScientificField() {
        return scientificField;
    }

    public void setScientificField(ScientificFieldDTO scientificField) {
        this.scientificField = scientificField;
    }

    public DepartmentDTO getDepartmentDTO() {
        return departmentDTO;
    }

    public void setDepartmentDTO(DepartmentDTO departmentDTO) {
        this.departmentDTO = departmentDTO;
    }
}
