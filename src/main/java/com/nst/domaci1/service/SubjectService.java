package com.nst.domaci1.service;

import com.nst.domaci1.domain.Subject;
import com.nst.domaci1.dto.SubjectChangeEspbDTO;
import com.nst.domaci1.dto.SubjectSaveUpdateDTO;

import java.util.List;

public interface SubjectService {

    Subject save(SubjectSaveUpdateDTO dto) throws Exception;

    List<Subject> getAll();

    void delete(Long id) throws Exception;

    Subject updateESPB(Long subjectId, SubjectChangeEspbDTO changeEspbDTO) throws Exception;

    Subject findById(Long id) throws Exception;
}
