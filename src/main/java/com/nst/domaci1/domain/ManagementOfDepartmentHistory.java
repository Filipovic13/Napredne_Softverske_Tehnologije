package com.nst.domaci1.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@IdClass(ManagementOfDepartmentHistoryID.class)
public class ManagementOfDepartmentHistory {

    @Id
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    private String supervisorFirstName;
    private String supervisorLastName;

    private String secretaryFirstName;
    private String secretaryLastName;


    @Id
    @ManyToOne
    @JoinColumn(name = "department_name", referencedColumnName = "name")
    private Department department;

    public ManagementOfDepartmentHistory() {

    }

    public ManagementOfDepartmentHistory(LocalDate startDate, LocalDate endDate, String supervisorFirstName, String supervisorLastName, String secretaryFirstName, String secretaryLastName, Department department) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.supervisorFirstName = supervisorFirstName;
        this.supervisorLastName = supervisorLastName;
        this.secretaryFirstName = secretaryFirstName;
        this.secretaryLastName = secretaryLastName;
        this.department = department;
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

    public String getSupervisorFirstName() {
        return supervisorFirstName;
    }

    public void setSupervisorFirstName(String supervisorFirstName) {
        this.supervisorFirstName = supervisorFirstName;
    }

    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    public void setSupervisorLastName(String supervisorLastName) {
        this.supervisorLastName = supervisorLastName;
    }

    public String getSecretaryFirstName() {
        return secretaryFirstName;
    }

    public void setSecretaryFirstName(String secretaryFirstName) {
        this.secretaryFirstName = secretaryFirstName;
    }

    public String getSecretaryLastName() {
        return secretaryLastName;
    }

    public void setSecretaryLastName(String secretaryLastName) {
        this.secretaryLastName = secretaryLastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
