package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.AcademicTitleConverter;
import com.nst.domaci1.domain.AcademicTitle;
import com.nst.domaci1.dto.AcademicTitleDTO;
import com.nst.domaci1.repository.AcademicTitleRepository;
import com.nst.domaci1.service.AcademicTitleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcademicTitleServiceImpl implements AcademicTitleService {

    private final AcademicTitleRepository academicTitleRepository;
    private final AcademicTitleConverter academicTitleConverter;

    @Override
    public AcademicTitleDTO save(AcademicTitleDTO dto) throws Exception {
        Optional<AcademicTitle> acTitleCodeDB = academicTitleRepository.findById(dto.getCode());
        if (acTitleCodeDB.isPresent()) {
            throw new Exception("Academic Title with this ID - code already exists!");
        }

        Optional<AcademicTitle> acTitleNameDB = academicTitleRepository.findByAcademicTitleName(dto.getName());
        if (acTitleNameDB.isPresent()) {
            throw new Exception("Academic Title with this name already exists!");
        }
        AcademicTitle academicTitle = academicTitleConverter.toEntity(dto);

        val savedAcademicTitle = academicTitleRepository.save(academicTitle);

        return academicTitleConverter.toDTO(savedAcademicTitle);
    }

    @Override
    public List<AcademicTitleDTO> getAll() {
        return academicTitleConverter.entitiesToDTOs(academicTitleRepository.findAll());
    }

    @Override
    public void delete(String code) throws Exception {
        Optional<AcademicTitle> acTitleDB = academicTitleRepository.findById(code);
        if (acTitleDB.isEmpty()) {
            throw new Exception("Academic Title doesn't exist with the given ID - code");
        }
        academicTitleRepository.deleteById(code);
    }

    @Override
    public AcademicTitleDTO findByName(String academicTitle) throws Exception {
        Optional<AcademicTitle> acTitleDB = academicTitleRepository.findByAcademicTitleName(academicTitle);
        if (acTitleDB.isEmpty()) {
            throw new Exception("Academic Title with the given name doesn't exist!");
        }

        return academicTitleConverter.toDTO(acTitleDB.get());
    }
}
