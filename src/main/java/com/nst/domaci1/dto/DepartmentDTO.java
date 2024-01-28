package com.nst.domaci1.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class DepartmentDTO implements Serializable {

    private String name;

    private String shortName;

    @Schema(nullable = true)
    private Long supervisorId;

    @Schema(nullable = true)
    private Long secretaryId;

    public DepartmentDTO() {
    }

    public DepartmentDTO(String name, String shortName, Long supervisorId, Long secretaryId) {
        this.name = name;
        this.shortName = shortName;
        this.supervisorId = supervisorId;
        this.secretaryId = secretaryId;
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

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Long getSecretaryId() {
        return secretaryId;
    }

    public void setSecretaryId(Long secretaryId) {
        this.secretaryId = secretaryId;
    }
}
