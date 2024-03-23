package com.nst.domaci1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scientific_field_codebook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScientificField {

    @Id
    @Column(name = "code")
    private String scientificFieldCode;

    @Column(name = "scientific_field")
    private String scientificFieldName;

    public void save(ScientificField scientificField) {
    }
}
