package com.nst.domaci1.service;

import com.nst.domaci1.domain.Member;
import com.nst.domaci1.domain.MemberID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MemberService {

    Member save(Member member) throws Exception;

    List<Member> getAll();

    Page<Member> getAll(Pageable pageable);

    void delete(MemberID id) throws Exception;

    Member updateScienceFields(String firstName, String lastName, String academicTitle, String educationTitle, String scienceField) throws Exception;

    Member updateDepartment(String firstName, String lastName, String departmentName) throws Exception;

    Member findById(MemberID id) throws Exception;
}
