package com.nst.domaci1.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "academic_title_code", referencedColumnName = "code")
    private AcademicTitle academicTitle;

    @ManyToOne
    @JoinColumn(name = "education_title_code", referencedColumnName = "code")
    private EducationTitle educationTitle;

    @ManyToOne
    @JoinColumn(name = "scientific_field_code", referencedColumnName = "code")
    private ScientificField scientificField;

    @Column(name = "department_name")
    private String department;

}

