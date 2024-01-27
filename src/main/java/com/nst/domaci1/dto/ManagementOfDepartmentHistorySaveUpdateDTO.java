package com.nst.domaci1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public class ManagementOfDepartmentHistorySaveUpdateDTO implements Serializable {

    @NotNull(message = "Start date must be entered!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date must be entered!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Manger role be entered!")
    private String managerRole;

    @NotNull(message = "Member ID must be entered!")
    private Long memberId;

    @NotBlank(message = "Department name must be entered!")
    private String departmentName;

    public ManagementOfDepartmentHistorySaveUpdateDTO() {
    }

    public ManagementOfDepartmentHistorySaveUpdateDTO(LocalDate startDate, LocalDate endDate, String managerRole, Long memberId, String departmentName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerRole = managerRole;
        this.memberId = memberId;
        this.departmentName = departmentName;
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

    public String getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(String managerRole) {
        this.managerRole = managerRole;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
