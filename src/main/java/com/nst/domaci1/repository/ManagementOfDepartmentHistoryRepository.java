package com.nst.domaci1.repository;

import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagementOfDepartmentHistoryID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ManagementOfDepartmentHistoryRepository extends JpaRepository<ManagementOfDepartmentHistory, ManagementOfDepartmentHistoryID> {

    Optional<ManagementOfDepartmentHistory> findFirstByDepartmentNameOrderByStartDateDesc(String departmentName);

    List<ManagementOfDepartmentHistory> findByDepartmentName(String departmentName);

}
