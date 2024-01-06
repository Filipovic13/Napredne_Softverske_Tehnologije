package com.nst.domaci1.repository;

import com.nst.domaci1.domain.Member;
import com.nst.domaci1.domain.MemberID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, MemberID> {
}
