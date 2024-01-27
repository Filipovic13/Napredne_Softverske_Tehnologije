package com.nst.domaci1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class SubjectSaveUpdateDTO implements Serializable {

    @NotBlank(message = "Subject name must be entered!")
    private String name;

    @Min(value = 2, message = "Value must be greater than or equal to 2")
    @Max(value = 6, message = "Value must be less than or equal to 6")
    private int espb;

    @NotBlank(message = "Department name must be entered!")
    private String departmenName;

    public SubjectSaveUpdateDTO() {
    }


    public SubjectSaveUpdateDTO(String name, int espb, String departmenName) {
        this.name = name;
        this.espb = espb;
        this.departmenName = departmenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEspb() {
        return espb;
    }

    public void setEspb(int espb) {
        this.espb = espb;
    }

    public String getDepartmenName() {
        return departmenName;
    }

    public void setDepartmenName(String departmenName) {
        this.departmenName = departmenName;
    }
}
