package com.nst.domaci1.repository;

import com.nst.domaci1.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findByName(String name);
}
