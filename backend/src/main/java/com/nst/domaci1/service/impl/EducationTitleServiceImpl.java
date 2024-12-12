package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.EducationTitleConverter;
import com.nst.domaci1.domain.EducationTitle;
import com.nst.domaci1.dto.EducationTitleDTO;
import com.nst.domaci1.repository.EducationTitleRepository;
import com.nst.domaci1.service.EducationTitleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EducationTitleServiceImpl implements EducationTitleService {

    private final EducationTitleRepository educationTitleRepository;
    private final EducationTitleConverter educationTitleConverter;


    @Override
    public EducationTitleDTO save(EducationTitleDTO dto) throws Exception {
        Optional<EducationTitle> edTitleCodeDB = educationTitleRepository.findById(dto.getCode());
        if (edTitleCodeDB.isPresent()) {
            throw new Exception("Education Title with this ID - code already exists!");
        }

        Optional<EducationTitle> edTitleNameDB = educationTitleRepository.findByEducationTitleName(dto.getName());
        if (edTitleNameDB.isPresent()) {
            throw new Exception("Education Title with this name already exists!");
        }

        val educationTitle = educationTitleConverter.toEntity(dto);

        val savedEdicationTitle = educationTitleRepository.save(educationTitle);

        return educationTitleConverter.toDTO(savedEdicationTitle);
    }

    @Override
    public List<EducationTitleDTO> getAll() {
        return educationTitleConverter.entitiesToDTOs(educationTitleRepository.findAll());
    }

    @Override
    public void delete(String code) throws Exception {
        Optional<EducationTitle> edTitleDB = educationTitleRepository.findById(code);
        if (edTitleDB.isEmpty()) {
            throw new Exception("Education Title  with the given ID - code doesn't exist!");
        }
        educationTitleRepository.deleteById(code);
    }

    @Override
    public EducationTitleDTO findByName(String educationTitle) throws Exception {
        Optional<EducationTitle> edTitleDB = educationTitleRepository.findByEducationTitleName(educationTitle);
        if (edTitleDB.isEmpty()) {
            throw new Exception("Education Title with the given name doesn't exist!");
        }
        return educationTitleConverter.toDTO(edTitleDB.get());
    }
}
