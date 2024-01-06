package com.nst.domaci1.repository;

import com.nst.domaci1.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcademicTitleHistoryRepository extends JpaRepository<AcademicTitleHistory, AcademicTitleHistoryID> {

    List<AcademicTitleHistory> findAllByMemberFirstNameAndMemberLastName(String firstName,String lastName);

    Optional<AcademicTitleHistory> findFirstByMemberFirstNameAndMemberLastNameOrderByStartDateDesc(String firstName, String lastName);
}
