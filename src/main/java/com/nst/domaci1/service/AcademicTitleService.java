package com.nst.domaci1.service;

import com.nst.domaci1.dto.AcademicTitleDTO;

import java.util.List;

public interface AcademicTitleService {

    AcademicTitleDTO save(AcademicTitleDTO academicTitle) throws Exception;

    List<AcademicTitleDTO> getAll();

    void delete(String code) throws Exception;

    AcademicTitleDTO findByName(String academicTitle) throws Exception;
}