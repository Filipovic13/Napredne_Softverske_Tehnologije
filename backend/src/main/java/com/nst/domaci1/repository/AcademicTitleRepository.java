package com.nst.domaci1.repository;

import com.nst.domaci1.domain.AcademicTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AcademicTitleRepository extends JpaRepository<AcademicTitle, String> {

    Optional<AcademicTitle> findByAcademicTitleName(String name);

    @Query("SELECT a.academicTitleName FROM AcademicTitle a")
    List<String> findAllAcademicTitleNames();


    @Modifying
    @Query("DELETE FROM AcademicTitleHistory ath WHERE ath.member.firstName = :firstName AND ath.member.lastName = :lastName")
    void deleteByMember(String firstName, String lastName);


}