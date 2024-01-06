package com.nst.domaci1.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ManagementOfDepartmentHistoryID implements Serializable {

    private LocalDate startDate;

    private String department;

    public ManagementOfDepartmentHistoryID() {
    }

    public ManagementOfDepartmentHistoryID(LocalDate startDate, String departmentName) {
        this.startDate = startDate;
        this.department = departmentName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagementOfDepartmentHistoryID that = (ManagementOfDepartmentHistoryID) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, department);
    }
}
