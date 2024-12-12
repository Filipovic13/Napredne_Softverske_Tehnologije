package com.nst.domaci1.service;

import com.nst.domaci1.dto.MemberChangeAllScienceFieldsDTO;
import com.nst.domaci1.dto.MemberChangeDepartmentDTO;
import com.nst.domaci1.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {

    MemberDTO save(MemberDTO dto) throws Exception;

    List<MemberDTO> getAll();

    Page<MemberDTO> getAll(Pageable pageable);

    void delete(Long id) throws Exception;

    MemberDTO updateScienceFields(Long memberId, MemberChangeAllScienceFieldsDTO changeScienceFieldsDTO) throws Exception;

    MemberDTO updateDepartment(Long memberId, MemberChangeDepartmentDTO changeDepartmentDTO) throws Exception;

    MemberDTO findById(Long id) throws Exception;

}
