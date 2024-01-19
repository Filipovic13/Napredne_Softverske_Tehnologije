package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.ScientificField;
import com.nst.domaci1.repository.ScientificFieldRepository;
import com.nst.domaci1.service.ScientificFieldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScientificFieldServiceImpl implements ScientificFieldService {

    private ScientificFieldRepository scientificFieldRepository;

    public ScientificFieldServiceImpl(ScientificFieldRepository scientificFieldRepository) {
        this.scientificFieldRepository = scientificFieldRepository;
    }

    @Override
    public ScientificField save(ScientificField scientificField) throws Exception {
        Optional<ScientificField> scFieldCodeDB = scientificFieldRepository.findById(scientificField.getScientificFieldCode());
        Optional<ScientificField> scFieldNameDB = scientificFieldRepository.findByScientificFieldName(scientificField.getScientificFieldName());
        if (scFieldCodeDB.isPresent()) {
            throw new Exception("Scientific Field with this ID - code already exists!");
        }
        if (scFieldNameDB.isPresent()) {
            throw new Exception("Scientific Field with this name already exists!");
        }

        return scientificFieldRepository.save(scientificField);
    }

    @Override
    public List<ScientificField> getAll() {
        return scientificFieldRepository.findAll();
    }

    @Override
    public void delete(String code) throws Exception {
        Optional<ScientificField> scFieldDB = scientificFieldRepository.findById(code);
        if (scFieldDB.isEmpty()) {
            throw new Exception("Scientific Field doesn't exist with the given ID - code!");
        }
        scientificFieldRepository.deleteById(code);
    }

    @Override
    public ScientificField findByName(String scientificField) throws Exception {
        Optional<ScientificField> scFieldDB = scientificFieldRepository.findByScientificFieldName(scientificField);
        if (scFieldDB.isEmpty()) {
            throw new Exception("Scientific Filed doesn't exist!");
        }
        return scFieldDB.get();
    }
}
