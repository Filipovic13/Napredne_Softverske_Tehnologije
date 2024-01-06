package com.nst.domaci1.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AcademicTitleHistoryID implements Serializable {

    private LocalDate startDate;

    private MemberID member;

    public AcademicTitleHistoryID() {

    }

    public AcademicTitleHistoryID(LocalDate startDate, MemberID member) {
        this.startDate = startDate;
        this.member = member;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public MemberID getMember() {
        return member;
    }

    public void setMember(MemberID member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicTitleHistoryID that = (AcademicTitleHistoryID) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, member);
    }
}
