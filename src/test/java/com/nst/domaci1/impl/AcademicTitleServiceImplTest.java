package com.nst.domaci1.impl;

import com.nst.domaci1.converter.impl.AcademicTitleConverter;
import com.nst.domaci1.domain.AcademicTitle;
import com.nst.domaci1.dto.AcademicTitleDTO;
import com.nst.domaci1.repository.AcademicTitleRepository;
import com.nst.domaci1.service.AcademicTitleService;
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
class AcademicTitleServiceImplTest {

    @Autowired
    private AcademicTitleService academicTitleService;

    @MockBean
    private AcademicTitleRepository academicTitleRepository;

    @MockBean
    private AcademicTitleConverter academicTitleConverter;


    private final AcademicTitle academicTitle = new AcademicTitle("#01", "Professor");
    private final AcademicTitle academicTitle2 = new AcademicTitle("#11", "Graduate Assistant");

    private final AcademicTitleDTO dto = new AcademicTitleDTO("#01", "Professor");
    private final AcademicTitleDTO dto2 = new AcademicTitleDTO("#11", "Graduate Assistant");

    @BeforeEach
    void setUp() {
        when(academicTitleConverter.toEntity(dto))
                .thenReturn(academicTitle);
        when(academicTitleConverter.toDTO(academicTitle))
                .thenReturn(dto);
    }

    @Test
    void saveSuccessTest() throws Exception {
        when(academicTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.empty());
        when(academicTitleRepository.findByAcademicTitleName(dto.getName()))
                .thenReturn(Optional.empty());
        when(academicTitleRepository.save(academicTitle))
                .thenReturn(academicTitle);

        val result = academicTitleRepository.save(academicTitle);

        assertNotNull(result);
        assertEquals(academicTitle, result);
    }

    @Test
    void saveFailureTest() throws Exception {
        when(academicTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.of(academicTitle));
        Exception ex = assertThrows(Exception.class, () -> {
            academicTitleService.save(dto);
        });
        assertEquals("Academic Title with this ID - code already exists!", ex.getMessage());
    }

    @Test
    void saveFailure2Test() throws Exception {
        when(academicTitleRepository.findByAcademicTitleName(dto.getName()))
                .thenReturn(Optional.of(academicTitle));
        Exception ex = assertThrows(Exception.class, () -> {
            academicTitleService.save(dto);
        });
        assertEquals("Academic Title with this name already exists!", ex.getMessage());
    }

    @Test
    void getAllSuccessTest() {
        List<AcademicTitle> list = Arrays.asList(academicTitle, academicTitle2);
        List<AcademicTitleDTO> listDTO = Arrays.asList(dto, dto2);

        when(academicTitleRepository.findAll()).thenReturn(list);
        when(academicTitleConverter.entitiesToDTOs(list))
                .thenReturn(listDTO);

        val result = academicTitleService.getAll();
        assertEquals(listDTO, result);
    }

    @Test
    void deleteSuccessTest() throws Exception {
        when(academicTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.of(academicTitle));
        academicTitleService.delete(dto.getCode());
        verify(academicTitleRepository, times(1)).deleteById(academicTitle.getAcademicTitleCode());
    }

    @Test
    void deleteFailureTest() {
        when(academicTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> {
            academicTitleService.delete(dto.getCode());
        });
        assertEquals("Academic Title doesn't exist with the given ID - code", ex.getMessage());
    }

    @Test
    void findByNameSuccessTest() throws Exception {
        when(academicTitleRepository.findByAcademicTitleName(dto.getName()))
                .thenReturn(Optional.of(academicTitle));
        val result = academicTitleService.findByName(dto.getName());
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void findByNameFailureTest() {
        when(academicTitleRepository.findByAcademicTitleName(dto.getName()))
                .thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> {
            academicTitleService.findByName(dto.getName());
        });
        assertEquals("Academic Title with the given name doesn't exist!", ex.getMessage());
    }
}

