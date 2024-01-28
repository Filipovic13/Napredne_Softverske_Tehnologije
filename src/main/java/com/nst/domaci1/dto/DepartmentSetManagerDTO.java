package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class DepartmentSetManagerDTO implements Serializable {

    @NotBlank(message = "Manager role must be entered!")
    private String managerRole;

    @Schema(nullable = true)
    private Long memberId;


    public DepartmentSetManagerDTO() {
    }

    public DepartmentSetManagerDTO(String managerRole, Long memberId) {
        this.managerRole = managerRole;
        this.memberId = memberId;
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


}
