package com.nst.domaci1.service;

import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.MemberChangeAllScienceFieldsDTO;
import com.nst.domaci1.dto.MemberChangeDepartmentDTO;
import com.nst.domaci1.dto.MemberSaveUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {

    Member save(MemberSaveUpdateDTO dto) throws Exception;

    List<Member> getAll();

    Page<Member> getAll(Pageable pageable);

    void delete(Long id) throws Exception;

    Member updateScienceFields(Long memberId, MemberChangeAllScienceFieldsDTO changeScienceFieldsDTO) throws Exception;

    Member updateDepartment(Long memberId, MemberChangeDepartmentDTO changeDepartmentDTO) throws Exception;

    Member findById(Long id) throws Exception;

}
