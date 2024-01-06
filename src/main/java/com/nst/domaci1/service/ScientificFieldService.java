package com.nst.domaci1.service;

import com.nst.domaci1.domain.ScientificField;

import java.util.List;

public interface ScientificFieldService {

    ScientificField save(ScientificField scientificField) throws Exception;

    List<ScientificField> getAll();

    void delete(String code) throws Exception;

    ScientificField findByName(String scientificField) throws Exception;
}
