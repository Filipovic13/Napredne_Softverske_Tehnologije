package com.nst.domaci1.impl;

import com.nst.domaci1.converter.impl.SubjectConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.Subject;
import com.nst.domaci1.dto.SubjectChangeEspbDTO;
import com.nst.domaci1.dto.SubjectDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.SubjectRepository;
import com.nst.domaci1.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubjectServiceImplTest {

    @Autowired
    private SubjectService subjectService;

    @MockBean
    private SubjectRepository subjectRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private SubjectConverter subjectConverter;


    private final Department department = new Department("Katedra za softversko inzenjerstvo", "SI", 1L, 2L);

    private final Subject subject = new Subject(null, "Programiranje 1", 4, department);

    private final SubjectDTO subjectDTO = new SubjectDTO(null, "Programiranje 1", 4, department.getName());

    private Subject subject2;

    private SubjectDTO subjectDTO2;

    @BeforeEach
    void setUp() {
        subject2 = new Subject(1L, "Programiranje 2", 6, department);

        subjectDTO2 = new SubjectDTO(1L, "Programiranje 2", 6, department.getName());

        when(subjectConverter.toEntity(subjectDTO))
                .thenReturn(subject);

        when(subjectConverter.toDTO(subject))
                .thenReturn(subjectDTO);

        when(subjectConverter.toDTO(subject2))
                .thenReturn(subjectDTO2);
    }

    @Test
    void saveSubjectSuccessTest() throws Exception {
        when(subjectRepository.findByName(subjectDTO.getName())).thenReturn(Optional.empty());
        when(departmentRepository.findById(subjectDTO.getDepartment())).thenReturn(Optional.of(department));

        when(subjectRepository.save(subject)).thenReturn(subject);
        SubjectDTO savedSubjectDto = subjectService.save(subjectDTO);

        assertNotNull(savedSubjectDto);
        assertEquals(subjectDTO, savedSubjectDto);
    }

    @Test
    void saveSubjectFailureTest() throws Exception {
        when(subjectRepository.findByName(subjectDTO.getName())).thenReturn(Optional.of(subject));
        assertThrows(Exception.class, () -> {
            subjectService.save(subjectDTO);
        });
    }

    @Test
    void getAllTest() {
        List<Subject> subjects = Arrays.asList(subject, subject2);
        when(subjectRepository.findAll()).thenReturn(subjects);
        List<SubjectDTO> subjectsDTO = Arrays.asList(subjectDTO, subjectDTO2);
        when(subjectConverter.entitiesToDTOs(subjects)).thenReturn(subjectsDTO);

        List<SubjectDTO> result = subjectService.getAll();
        assertEquals(subjectsDTO, result);
    }

    @Test
    void deleteSuccessTest() throws Exception {
        when(subjectRepository.findById(subjectDTO2.getId())).thenReturn(Optional.of(subject2));
        subjectService.delete(subject2.getId());
        verify(subjectRepository, times(1)).delete(subject2);
    }

    @Test
    void deleteFailureTest() throws Exception {
        when(subjectRepository.findById(subjectDTO2.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            subjectService.delete(subjectDTO2.getId());
        });
    }

    @Test
    void updateESPBSuccessTest() throws Exception {
        SubjectChangeEspbDTO dto = new SubjectChangeEspbDTO(5);
        when(subjectRepository.findById(subject2.getId())).thenReturn(Optional.of(subject2));
        subject2.setEspb(dto.getEspb());
        subjectDTO2.setEspb(dto.getEspb());

        when(subjectRepository.save(subject2)).thenReturn(subject2);
        when(subjectConverter.toDTO(subject2)).thenReturn(subjectDTO2);

        SubjectDTO result = subjectService.updateESPB(subject2.getId(), dto);
        assertNotNull(result);
        assertEquals(subjectDTO2, result);
    }

    @Test
    void updateESPBFailureTest() throws Exception {
        SubjectChangeEspbDTO dto = new SubjectChangeEspbDTO(5);
        when(subjectRepository.findById(subject2.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            subjectService.updateESPB(subject2.getId(), dto);
        });
    }

    @Test
    void findByIdSuccessTest() throws Exception {
        when(subjectRepository.findById(subject2.getId())).thenReturn(Optional.of(subject2));
        SubjectDTO result = subjectService.findById(subject2.getId());
        assertNotNull(result);
        assertEquals(subjectDTO2, result);
    }

    @Test
    void findByIdFailureTest() throws Exception {
        when(subjectRepository.findById(subject2.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            subjectService.findById(subject2.getId());
        });
    }
}