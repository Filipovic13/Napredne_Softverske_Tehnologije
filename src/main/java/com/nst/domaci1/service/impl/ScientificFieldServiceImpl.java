package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.ScientificFieldConverter;
import com.nst.domaci1.domain.ScientificField;
import com.nst.domaci1.dto.ScientificFieldDTO;
import com.nst.domaci1.repository.ScientificFieldRepository;
import com.nst.domaci1.service.ScientificFieldService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ScientificFieldServiceImpl implements ScientificFieldService {

    private final ScientificFieldRepository scientificFieldRepository;
    private final ScientificFieldConverter scientificFieldConverter;


    @Override
    public ScientificFieldDTO save(ScientificFieldDTO dto) throws Exception {

        Optional<ScientificField> scFieldCodeDB = scientificFieldRepository.findById(dto.getCode());
        if (scFieldCodeDB.isPresent()) {
            throw new Exception("Scientific Field with this ID - code already exists!");
        }

        Optional<ScientificField> scFieldNameDB = scientificFieldRepository.findByScientificFieldName(dto.getName());
        if (scFieldNameDB.isPresent()) {
            throw new Exception("Scientific Field with this name already exists!");
        }
        val scientificField = scientificFieldConverter.toEntity(dto);

        val saved = scientificFieldRepository.save(scientificField);

        return scientificFieldConverter.toDTO(saved);
    }

    @Override
    public List<ScientificFieldDTO> getAll() {
        return scientificFieldConverter.entitiesToDTOs(scientificFieldRepository.findAll());
    }

    @Override
    public void delete(String code) throws Exception {
        Optional<ScientificField> scFieldDB = scientificFieldRepository.findById(code);
        if (scFieldDB.isEmpty()) {
            throw new Exception("Scientific Field with the given ID - code doesn't exist!");
        }
        scientificFieldRepository.deleteById(code);
    }

    @Override
    public ScientificFieldDTO findByName(String scientificField) throws Exception {
        Optional<ScientificField> scFieldDB = scientificFieldRepository.findByScientificFieldName(scientificField);
        if (scFieldDB.isEmpty()) {
            throw new Exception("Scientific Filed with the given name doesn't exist!");
        }
        return scientificFieldConverter.toDTO(scFieldDB.get());
    }
}
