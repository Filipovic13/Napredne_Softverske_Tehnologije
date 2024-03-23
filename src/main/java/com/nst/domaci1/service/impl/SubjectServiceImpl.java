package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.SubjectConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.Subject;
import com.nst.domaci1.dto.SubjectChangeEspbDTO;
import com.nst.domaci1.dto.SubjectDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.SubjectRepository;
import com.nst.domaci1.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final SubjectConverter subjectConverter;


    @Override
    public SubjectDTO save(SubjectDTO dto) throws Exception {

        Optional<Subject> subjDB = subjectRepository.findByName(dto.getName());
        if (subjDB.isPresent()) {
            throw new Exception("Subject with the given name already exists!");
        }

        Optional<Department> depDB = departmentRepository.findById(dto.getDepartment());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! " +
                    "\nEnter one of next values: \n" + departmentRepository.findAllNames());
        }

        Subject newSubject = subjectConverter.toEntity(dto);

        final Subject savedSubject = subjectRepository.save(newSubject);

        return subjectConverter.toDTO(savedSubject);
    }

    @Override
    public List<SubjectDTO> getAll() {
        return subjectConverter.entitiesToDTOs(subjectRepository.findAll());
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
    public SubjectDTO updateESPB(Long subjectId, SubjectChangeEspbDTO changeEspbDTO) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(subjectId);
        if (subjDB.isEmpty()) {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
        Subject subject = subjDB.get();
        subject.setEspb(changeEspbDTO.getEspb());

        final Subject updatedSubject = subjectRepository.save(subject);

        return subjectConverter.toDTO(updatedSubject);
    }

    @Override
    public SubjectDTO findById(Long id) throws Exception {
        Optional<Subject> subjDB = subjectRepository.findById(id);
        if (subjDB.isEmpty()) {
            throw new Exception("Subject with the given ID doesn't exist!");
        }
        return subjectConverter.toDTO(subjDB.get());
    }
}
