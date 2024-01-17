package com.nst.domaci1.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table
@IdClass(MemberID.class)
public class Member {

    @Id
    @Column(name = "first_name")
    private String firstName;

    @Id
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ManagerRole managerRole;

    @ManyToOne
    @JoinColumn(name = "academic_title_code", referencedColumnName = "code")
    private AcademicTitle academicTitle;

    @ManyToOne
    @JoinColumn(name = "education_title_code", referencedColumnName = "code")
    private EducationTitle educationTitle;

    @ManyToOne
    @JoinColumn(name = "scientific_field_code", referencedColumnName = "code")
    private ScientificField scientificField;

    @ManyToOne
    @JoinColumn(name = "department_name", referencedColumnName = "name")
    private Department department;


    public Member() {
    }

    public Member(String firstName, String lastName, ManagerRole managerRole, AcademicTitle academicTitle, EducationTitle educationTitle, ScientificField scientificField, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerRole = managerRole;
        this.academicTitle = academicTitle;
        this.educationTitle = educationTitle;
        this.scientificField = scientificField;
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }

    public EducationTitle getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(EducationTitle educationTitle) {
        this.educationTitle = educationTitle;
    }

    public ScientificField getScientificField() {
        return scientificField;
    }

    public void setScientificField(ScientificField scientificField) {
        this.scientificField = scientificField;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ManagerRole getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(ManagerRole managerRole) {
        this.managerRole = managerRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(new MemberID(firstName, lastName), new MemberID(member.firstName, member.lastName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
