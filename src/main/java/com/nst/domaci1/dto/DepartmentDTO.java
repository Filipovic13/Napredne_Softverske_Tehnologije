package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class DepartmentDTO implements Serializable {

    @NotNull(message = "Department name must be entered!")
    private String name;

    @NotNull(message = "Department short must be entered!")
    private String shortName;

    public DepartmentDTO() {
    }

    public DepartmentDTO(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
