package com.nst.domaci1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nst.domaci1.domain.ManagerRole;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public class ManagementOfDepartmentHistoryDTO implements Serializable {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private ManagerRole managerRole;

    private MemberDTO memberDTO;

    private DepartmentDTO departmentDTO;

    public ManagementOfDepartmentHistoryDTO() {
    }

    public ManagementOfDepartmentHistoryDTO(Long id, LocalDate startDate, LocalDate endDate, ManagerRole managerRole, MemberDTO memberDTO, DepartmentDTO departmentDTO) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerRole = managerRole;
        this.memberDTO = memberDTO;
        this.departmentDTO = departmentDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ManagerRole getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(ManagerRole managerRole) {
        this.managerRole = managerRole;
    }

    public MemberDTO getMemberDTO() {
        return memberDTO;
    }

    public void setMemberDTO(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    public DepartmentDTO getDepartmentDTO() {
        return departmentDTO;
    }

    public void setDepartmentDTO(DepartmentDTO departmentDTO) {
        this.departmentDTO = departmentDTO;
    }
}
