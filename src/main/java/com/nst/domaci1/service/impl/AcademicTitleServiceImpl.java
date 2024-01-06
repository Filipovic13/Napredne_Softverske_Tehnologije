package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.AcademicTitle;
import com.nst.domaci1.repository.AcademicTitleRepository;
import com.nst.domaci1.service.AcademicTitleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicTitleServiceImpl implements AcademicTitleService {

    AcademicTitleRepository academicTitleRepository;

    public AcademicTitleServiceImpl(AcademicTitleRepository academicTitleRepository) {
        this.academicTitleRepository = academicTitleRepository;
    }

    @Override
    public AcademicTitle save(AcademicTitle academicTitle) throws Exception {
        Optional<AcademicTitle> acTitleCodeDB = academicTitleRepository.findById(academicTitle.getAcademicTitleCode());
        Optional<AcademicTitle> acTitleNameDB = academicTitleRepository.findByAcademicTitleName(academicTitle.getAcademicTitleName());
        if (acTitleCodeDB.isPresent()){
            throw new Exception("Academic Title with this ID - code already exists!");
        }
        if (acTitleNameDB.isPresent()){
            throw new Exception("Academic Title with this name already exists!");
        }
        return academicTitleRepository.save(academicTitle);
    }

    @Override
    public List<AcademicTitle> getAll() {
        return academicTitleRepository.findAll();
    }

    @Override
    public void delete(String code) throws Exception {
        Optional<AcademicTitle> acTitleDB = academicTitleRepository.findById(code);
        if (acTitleDB.isEmpty()){
            throw new Exception("Academic Title doesn't exist with the given ID - code");
        }
        academicTitleRepository.deleteById(code);
    }

    @Override
    public AcademicTitle findByName(String academicTitle) throws Exception {
        Optional<AcademicTitle> acTitleDB = academicTitleRepository.findByAcademicTitleName(academicTitle);
        if (acTitleDB.isEmpty()){
            throw new Exception("Academic Title doesn't exist!");
        }
        return acTitleDB.get();
    }
}
