package com.nst.domaci1.service;

import com.nst.domaci1.domain.EducationTitle;

import java.util.List;

public interface EducationTitleService {

    EducationTitle save(EducationTitle educationTitle) throws Exception;

    List<EducationTitle> getAll();

    void delete(String code) throws Exception;

    EducationTitle findByName(String educationTitle) throws Exception;
}
