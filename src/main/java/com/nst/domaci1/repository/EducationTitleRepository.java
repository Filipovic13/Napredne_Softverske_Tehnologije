package com.nst.domaci1.repository;

import com.nst.domaci1.domain.EducationTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EducationTitleRepository extends JpaRepository<EducationTitle, String> {

    Optional<EducationTitle> findByEducationTitleName(String name);

    @Query("SELECT e.educationTitleName FROM EducationTitle e")
    List<String> findAllEducationTitlesNames();
}