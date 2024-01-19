package com.nst.domaci1.repository;

import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagerRole;
import com.nst.domaci1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagementOfDepartmentHistoryRepository extends JpaRepository<ManagementOfDepartmentHistory, Long> {

    List<ManagementOfDepartmentHistory> findByDepartmentOrderByStartDateDesc(Department department);

    Optional<ManagementOfDepartmentHistory> findFirstByDepartmentAndManagerRoleOrderByStartDateDesc(Department department, ManagerRole managerRole);

    List<ManagementOfDepartmentHistory> findByMemberAndDepartmentOrderByStartDateDesc(Member member, Department department);
}
