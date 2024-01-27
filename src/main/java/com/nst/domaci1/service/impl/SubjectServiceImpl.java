package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.Subject;
import com.nst.domaci1.dto.SubjectChangeEspbDTO;
import com.nst.domaci1.dto.SubjectSaveUpdateDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.SubjectRepository;
import com.nst.domaci1.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;
    private DepartmentRepository departmentRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, DepartmentRepository departmentRepository) {
        this.subjectRepository = subjectRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Subject save(SubjectSaveUpdateDTO dto) throws Exception {

        Optional<Subject> subjDB = subjectRepository.findByName(dto.getName());
        if (subjDB.isPresent()) {
            throw new Exception("Subject with the given name already exists!");
        }

        Optional<Department> depDB = departmentRepository.findById(dto.getDepartmenName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
        Subject newSubject = new Subject(null, dto.getName(), dto.getEspb(), depDB.get());
        return subjectRepository.save(newSubject);
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(id);
        if (subjDB.isEmpty()) {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
        subjectRepository.delete(subjDB.get());
    }

    @Override
    public Subject updateESPB(Long subjectId, SubjectChangeEspbDTO changeEspbDTO) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(subjectId);
        if (subjDB.isEmpty()) {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
        Subject subject = subjDB.get();
        subject.setEspb(changeEspbDTO.getEspb());

        return subjectRepository.save(subject);
    }

    @Override
    public Subject findById(Long id) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(id);
        if (subjDB.isEmpty()) {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
        return subjDB.get();
    }
}
