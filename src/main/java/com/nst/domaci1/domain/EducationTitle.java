package com.nst.domaci1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "education_title_codebook")
public class EducationTitle {

    @Id
    @Column(name = "code")
    private String educationTitleCode;

    @Column(name = "education_title")
    private String educationTitleName;

    public EducationTitle() {
    }

    public EducationTitle(String code, String educationTitleName) {
        this.educationTitleCode = code;
        this.educationTitleName = educationTitleName;
    }

    public String getEducationTitleCode() {
        return educationTitleCode;
    }

    public void setEducationTitleCode(String educationTitleCode) {
        this.educationTitleCode = educationTitleCode;
    }

    public String getEducationTitleName() {
        return educationTitleName;
    }

    public void setEducationTitleName(String educationTitleName) {
        this.educationTitleName = educationTitleName;
    }
}
