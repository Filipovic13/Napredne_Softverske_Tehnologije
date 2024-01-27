package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class MemberChangeDepartmentDTO implements Serializable {

    @NotBlank(message = "Department name must be entered!")
    private String departmentName;

    public MemberChangeDepartmentDTO() {
    }

    public MemberChangeDepartmentDTO(String departmentName) {
        this.departmentName = departmentName;
    }


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
