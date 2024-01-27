package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class EducationTitleDTO implements Serializable {

    @NotNull(message = "Education title code must be entered!")
    private String code;

    @NotNull(message = "Education title name must be entered!")
    private String name;

    public EducationTitleDTO() {
    }

    public EducationTitleDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
