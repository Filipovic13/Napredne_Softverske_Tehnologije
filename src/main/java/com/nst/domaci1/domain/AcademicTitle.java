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
@Table(name = "academic_title_codebook")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicTitle {

    @Id
    @Column(name = "code")
    private String academicTitleCode;

    @Column(name = "academic_title")
    private String academicTitleName;

}