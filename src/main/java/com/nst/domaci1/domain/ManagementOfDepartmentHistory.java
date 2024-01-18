package com.nst.domaci1.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class ManagementOfDepartmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ManagerRole managerRole;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "department_name", referencedColumnName = "name")
    private Department department;

    public ManagementOfDepartmentHistory() {

    }

    public ManagementOfDepartmentHistory(Long id, LocalDate startDate, LocalDate endDate, ManagerRole managerRole, Member member, Department department) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerRole = managerRole;
        this.member = member;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ManagerRole getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(ManagerRole managerRole) {
        this.managerRole = managerRole;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
