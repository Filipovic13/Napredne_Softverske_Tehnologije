package com.nst.domaci1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public class ManagementOfDepartmentHistoryDTO implements Serializable {

    @NotNull(message = "Start date must be entered!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date must be entered!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String supervisorFirstName;
    private String supervisorLastName;

    private String secretaryFirstName;
    private String secretaryLastName;

    private DepartmentDTO departmentDTO;

    public ManagementOfDepartmentHistoryDTO() {
    }

    public ManagementOfDepartmentHistoryDTO(LocalDate startDate, LocalDate endDate, String supervisorFirstName, String supervisorLastName, String secretaryFirstName, String secretaryLastName, DepartmentDTO departmentDTO) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.supervisorFirstName = supervisorFirstName;
        this.supervisorLastName = supervisorLastName;
        this.secretaryFirstName = secretaryFirstName;
        this.secretaryLastName = secretaryLastName;
        this.departmentDTO = departmentDTO;
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

    public DepartmentDTO getDepartmentDTO() {
        return departmentDTO;
    }

    public void setDepartmentDTO(DepartmentDTO departmentDTO) {
        this.departmentDTO = departmentDTO;
    }
}
