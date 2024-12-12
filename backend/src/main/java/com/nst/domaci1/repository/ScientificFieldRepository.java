package com.nst.domaci1.repository;

import com.nst.domaci1.domain.ScientificField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScientificFieldRepository extends JpaRepository<ScientificField, String> {

    Optional<ScientificField> findByScientificFieldName(String name);

    @Query("SELECT sf.scientificFieldName FROM ScientificField sf")
    List<String> findAllScientificFieldNames();
}
