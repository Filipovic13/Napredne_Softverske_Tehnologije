package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.EducationTitle;
import com.nst.domaci1.repository.EducationTitleRepository;
import com.nst.domaci1.service.EducationTitleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationTitleServiceImpl implements EducationTitleService {

    private EducationTitleRepository educationTitleRepository;

    public EducationTitleServiceImpl(EducationTitleRepository educationTitleRepository) {
        this.educationTitleRepository = educationTitleRepository;
    }

    @Override
    public EducationTitle save(EducationTitle educationTitle) throws Exception {
        Optional<EducationTitle> edTitleCodeDB = educationTitleRepository.findById(educationTitle.getEducationTitleCode());
        Optional<EducationTitle> edTitleNameDB = educationTitleRepository.findByEducationTitleName(educationTitle.getEducationTitleName());
        if (edTitleCodeDB.isPresent()) {
            throw new Exception("Education Title with this ID - code already exists!");
        }
        if (edTitleNameDB.isPresent()) {
            throw new Exception("Education Title with this name already exists!");
        }

        return educationTitleRepository.save(educationTitle);
    }

    @Override
    public List<EducationTitle> getAll() {
        return educationTitleRepository.findAll();
    }

    @Override
    public void delete(String code) throws Exception {
        Optional<EducationTitle> edTitleDB = educationTitleRepository.findById(code);
        if (edTitleDB.isEmpty()) {
            throw new Exception("Education Title doesn't exist with the given ID - code!");
        }
        educationTitleRepository.deleteById(code);
    }

    @Override
    public EducationTitle findByName(String educationTitle) throws Exception {
        Optional<EducationTitle> edTitleDB = educationTitleRepository.findByEducationTitleName(educationTitle);
        if (edTitleDB.isEmpty()) {
            throw new Exception("Education Title doesn't exist!");
        }
        return edTitleDB.get();
    }
}
