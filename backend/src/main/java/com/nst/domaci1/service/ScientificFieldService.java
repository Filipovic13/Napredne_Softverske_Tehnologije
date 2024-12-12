package com.nst.domaci1.service;

import com.nst.domaci1.dto.ScientificFieldDTO;

import java.util.List;

public interface ScientificFieldService {

    ScientificFieldDTO save(ScientificFieldDTO scientificField) throws Exception;

    List<ScientificFieldDTO> getAll();

    void delete(String code) throws Exception;

    ScientificFieldDTO findByName(String scientificField) throws Exception;
}
