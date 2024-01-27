package com.nst.domaci1.repository;

import com.nst.domaci1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);
}