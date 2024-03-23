package com.nst.domaci1.impl;

import com.nst.domaci1.converter.impl.DepartmentConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.service.DepartmentService;
import lombok.val;
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
class DepartmnetServiceImplTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    private final Department department = new Department(
            "Katedra za operaciona istrazivanja i statistiku",
            "Oi & Stat",
            5L,
            8L
            );

    private Department department2 = new Department(
            "Katedra za matematiku",
            "Mata",
            9L,
            12L
    );

    private final DepartmentDTO dto = new DepartmentDTO(
            "Katedra za operaciona istrazivanja i statistiku",
            "Oi & Stat",
            5L,
            8L
    );

    private DepartmentDTO dto2 = new DepartmentDTO(
            "Katedra za matematiku",
            "Mata",
            9L,
            12L
    );


    @MockBean
    private DepartmentConverter departmentConverter;

    @BeforeEach
    void setUp() {
        when(departmentConverter.toEntity(dto))
                .thenReturn(department);


        when(departmentConverter.toDTO(department))
                .thenReturn(dto);

    }

    @Test
    void saveSuccessTest() throws Exception {
        when(departmentRepository.findById(dto.getName()))
                .thenReturn(Optional.empty());
        when(departmentRepository.save(department))
                .thenReturn(department);

        val result = departmentService.save(dto);

        assertNotNull(result);
        assertEquals(dto,result);
    }

    @Test
    void saveFailureTest() throws Exception {
        when(departmentRepository.findById(dto.getName()))
                .thenReturn(Optional.of(department));
        Exception ex = assertThrows(Exception.class, () -> {
            departmentService.save(dto);
        });
        assertEquals("Department with the given name already exists!", ex.getMessage());
    }

    @Test
    void getAllSuccessTest()  {
        List<Department> list = Arrays.asList(department, department2);
        List<DepartmentDTO> listDTO = Arrays.asList(dto, dto2);

        when(departmentRepository.findAll()).thenReturn(list);
        when(departmentConverter.entitiesToDTOs(list)).thenReturn(listDTO);

        val result = departmentService.getAll();

        assertEquals(listDTO, result);
    }


    @Test
    void deleteSuccessTest() throws Exception {
        when(departmentRepository.findById(dto.getName()))
                .thenReturn(Optional.of(department));
        departmentService.delete(dto.getName());
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    void deleteFailureTest(){
        when(departmentRepository.findById(dto.getName()))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, ()-> {
            departmentService.delete(dto.getName());
        });
    }

    @Test
    void findByIdSuccessTest() throws Exception {
        when(departmentRepository.findById(dto.getName()))
                .thenReturn(Optional.of(department));
        val result = departmentService.findById(dto.getName());
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void findByIdFailureTest() throws Exception {
        when(departmentRepository.findById(dto.getName()))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            departmentService.findById(dto.getName());
        });
    }
}