package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.Subject;
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
    public Subject save(Subject subject) throws Exception {

        Optional<Subject> subjDB = subjectRepository.findByName(subject.getName());
        if (subjDB.isPresent()) {
            throw new Exception("Subject with the given name already exists!");
        } else {

            Optional<Department> depDB = departmentRepository.findById(subject.getDepartment().getName());
            if (depDB.isEmpty()) {
                throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
            }
            subject.setDepartment(depDB.get());
            return subjectRepository.save(subject);
        }
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(id);
        if (subjDB.isPresent()) {
            subjectRepository.delete(subjDB.get());
        } else {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
    }

    @Override
    public Subject update(Long id, int espb) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(id);
        if (subjDB.isEmpty()) {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
        Subject subject = subjDB.get();
        subject.setEspb(espb);

        return subjectRepository.save(subject);
    }

    @Override
    public Subject findById(Long id) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(id);
        if (subjDB.isPresent()) {
            return subjDB.get();
        } else {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
    }
}
