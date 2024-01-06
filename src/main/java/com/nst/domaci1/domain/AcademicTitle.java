package com.nst.domaci1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "academic_title_codebook")
public class AcademicTitle {

    @Id
    @Column(name = "code")
    private String academicTitleCode;

    @Column(name = "academic_title")
    private String academicTitleName;


    public AcademicTitle() {
            
    }

    public AcademicTitle(String code, String academicTitleName) {
        this.academicTitleCode = code;
        this.academicTitleName = academicTitleName;
    }

    public String getAcademicTitleCode() {
        return academicTitleCode;
    }

    public void setAcademicTitleCode(String academicTitleCode) {
        this.academicTitleCode = academicTitleCode;
    }

    public String getAcademicTitleName() {
        return academicTitleName;
    }

    public void setAcademicTitleName(String academicTitleName) {
        this.academicTitleName = academicTitleName;
    }
}