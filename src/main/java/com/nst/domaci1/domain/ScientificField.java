package com.nst.domaci1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "scientific_field_codebook")
public class ScientificField {

    @Id
    @Column(name = "code")
    private String scientificFieldCode;

    @Column(name = "scientific_field")
    private String scientificFieldName;




    public ScientificField() {

    }

    public ScientificField(String code, String scientificFieldName) {
        this.scientificFieldCode = code;
        this.scientificFieldName = scientificFieldName;
    }

    public String getScientificFieldCode() {
        return scientificFieldCode;
    }

    public void setScientificFieldCode(String scientificFieldCode) {
        this.scientificFieldCode = scientificFieldCode;
    }

    public String getScientificFieldName() {
        return scientificFieldName;
    }

    public void setScientificFieldName(String scientificFieldName) {
        this.scientificFieldName = scientificFieldName;
    }
}
