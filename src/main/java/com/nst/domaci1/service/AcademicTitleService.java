package com.nst.domaci1.service;

import com.nst.domaci1.domain.AcademicTitle;

import java.util.List;

public interface AcademicTitleService {

    AcademicTitle save(AcademicTitle academicTitle) throws Exception;

    List<AcademicTitle> getAll();

    void delete(String code) throws Exception;

    AcademicTitle findByName(String academicTitle) throws Exception;
}