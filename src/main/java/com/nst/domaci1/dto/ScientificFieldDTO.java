package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class ScientificFieldDTO implements Serializable {

    @NotBlank(message = "Scientific field code must be entered!")
    private String code;

    @NotBlank(message = "Scientific field name must be entered!")
    private String name;

    public ScientificFieldDTO() {
    }

    public ScientificFieldDTO(String code, String name) {
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
