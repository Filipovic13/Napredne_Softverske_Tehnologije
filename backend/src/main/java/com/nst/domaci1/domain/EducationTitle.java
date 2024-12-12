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
@Table(name = "education_title_codebook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationTitle {

    @Id
    @Column(name = "code")
    private String educationTitleCode;

    @Column(name = "education_title")
    private String educationTitleName;

}
