package com.nst.domaci1.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@IdClass(AcademicTitleHistoryID.class)
public class AcademicTitleHistory {

    @Id
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "academic_title_code", referencedColumnName = "code")
    private AcademicTitle academicTitle;

    @ManyToOne
    @JoinColumn(name = "scientific_field_code", referencedColumnName = "code")
    private ScientificField scientificField;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "member_first_name", referencedColumnName = "first_name"),
            @JoinColumn(name = "member_last_name", referencedColumnName = "last_name")
    })
    private Member member;


    public AcademicTitleHistory() {
    }

    public AcademicTitleHistory(LocalDate startDate, LocalDate endDate, AcademicTitle academicTitle, ScientificField scientificField, Member member) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicTitle = academicTitle;
        this.scientificField = scientificField;
        this.member = member;
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

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }

    public ScientificField getScientificField() {
        return scientificField;
    }

    public void setScientificField(ScientificField scientificField) {
        this.scientificField = scientificField;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
