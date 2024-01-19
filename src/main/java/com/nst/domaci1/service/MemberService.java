package com.nst.domaci1.service;

import com.nst.domaci1.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {

    Member save(Member member) throws Exception;

    List<Member> getAll();

    Page<Member> getAll(Pageable pageable);

    void delete(Long id) throws Exception;

    Member updateScienceFields(Long memberId, String academicTitle, String educationTitle, String scienceField) throws Exception;

    Member updateDepartment(Long memberId, String departmentName) throws Exception;

    Member findById(Long id) throws Exception;

}
