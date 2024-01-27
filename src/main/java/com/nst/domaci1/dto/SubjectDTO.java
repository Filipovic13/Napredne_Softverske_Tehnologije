package com.nst.domaci1.dto;


import java.io.Serializable;

public class SubjectDTO implements Serializable {

    private Long id;

    private String name;

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
