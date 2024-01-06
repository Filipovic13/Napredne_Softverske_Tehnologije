package com.nst.domaci1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

public class SubjectDTO implements Serializable {
    private Long id;

    private String name;

    @Min(value = 2, message = "Value must be greater than or equal to 2")
    @Max(value = 6, message = "Value must be less than or equal to 6")
    private int espb;

    private DepartmentDTO departmentDTO;

    public SubjectDTO() {
    }

    public SubjectDTO(Long id, String name, int espb, DepartmentDTO departmentDTO) {
        this.id = id;
        this.name = name;
        this.espb = espb;
        this.departmentDTO = departmentDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DepartmentDTO getDepartmentDTO() {
        return departmentDTO;
    }

    public void setDepartmentDTO(DepartmentDTO departmentDTO) {
        this.departmentDTO = departmentDTO;
    }
}
