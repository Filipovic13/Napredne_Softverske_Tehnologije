package com.nst.domaci1.service;

import com.nst.domaci1.dto.EducationTitleDTO;

import java.util.List;

public interface EducationTitleService {

    EducationTitleDTO save(EducationTitleDTO educationTitle) throws Exception;

    List<EducationTitleDTO> getAll();

    void delete(String code) throws Exception;

    EducationTitleDTO findByName(String educationTitle) throws Exception;
}
