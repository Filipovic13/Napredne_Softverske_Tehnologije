package com.nst.domaci1.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table
public class Department {

    @Id
    private String name;

    private String shortName;

    private Long supervisorId;

    private Long secretaryId;

    public Department() {

    }

    public Department(String name, String shortName, Long supervisorId, Long secretaryId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department d = (Department) o;
        return Objects.equals(name, d.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
