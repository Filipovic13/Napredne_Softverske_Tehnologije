package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class AcademicTitleDTO implements Serializable {

    private String code;

    @NotNull
    private String name;

    public AcademicTitleDTO() {

    }

    public AcademicTitleDTO(String code, String name) {
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
