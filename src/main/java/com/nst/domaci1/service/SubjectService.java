package com.nst.domaci1.service;

import com.nst.domaci1.dto.SubjectChangeEspbDTO;
import com.nst.domaci1.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {

    SubjectDTO save(SubjectDTO dto) throws Exception;

    List<SubjectDTO> getAll();

    void delete(Long id) throws Exception;

    SubjectDTO updateESPB(Long subjectId, SubjectChangeEspbDTO changeEspbDTO) throws Exception;

    SubjectDTO findById(Long id) throws Exception;
}
