package com.nst.domaci1.impl;

import com.nst.domaci1.converter.impl.ScientificFieldConverter;
import com.nst.domaci1.domain.ScientificField;
import com.nst.domaci1.dto.ScientificFieldDTO;
import com.nst.domaci1.repository.ScientificFieldRepository;
import com.nst.domaci1.service.ScientificFieldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScientificFieldServiceImplTest {

    @Autowired
    private ScientificFieldService scientificFieldService;

    @MockBean
    private ScientificFieldRepository scientificFieldRepository;

    @MockBean
    private ScientificFieldConverter scientificFieldConverter;

    private final ScientificField scientificField = new ScientificField("#05", "Software Testing and Quality Assurance");
    private final ScientificField scientificField2 = new ScientificField("#06", "Business Analysis");

    private final ScientificFieldDTO scientificFieldDTO = new ScientificFieldDTO("#05", "Software Testing and Quality Assurance");
    private final ScientificFieldDTO scientificFieldDTO2 = new ScientificFieldDTO("#06", "Business Analysis");

    @BeforeEach
    void setUp() {
        when(scientificFieldConverter.toDTO(scientificField))
                .thenReturn(scientificFieldDTO);

        when(scientificFieldConverter.toEntity(scientificFieldDTO))
                .thenReturn(scientificField);
    }

    @Test
    void saveSuccessTest() throws Exception {
        when(scientificFieldRepository.findById(scientificFieldDTO.getCode())).thenReturn(Optional.empty());
        when(scientificFieldRepository.findByScientificFieldName(scientificFieldDTO.getName())).thenReturn(Optional.empty());
        when(scientificFieldRepository.save(scientificField)).thenReturn(scientificField);

        ScientificFieldDTO savedScientificFieldDTO = scientificFieldService.save(scientificFieldDTO);

        assertNotNull(savedScientificFieldDTO);
        assertEquals(scientificFieldDTO, savedScientificFieldDTO);
    }

    @Test
    void saveFailureTest() throws Exception {
        when(scientificFieldRepository.findById(scientificFieldDTO.getCode())).thenReturn(Optional.of(scientificField));
        assertThrows(Exception.class, () -> {
            scientificFieldService.save(scientificFieldDTO);
        });
    }

    @Test
    void saveFailure2Test() throws Exception {
        when(scientificFieldRepository.findByScientificFieldName(scientificFieldDTO.getName())).thenReturn(Optional.of(scientificField));
        assertThrows(Exception.class, () -> {
            scientificFieldService.save(scientificFieldDTO);
        });
    }

    @Test
    void getAllTest(){
        List<ScientificField> scientificFields = Arrays.asList(scientificField, scientificField2);
        List<ScientificFieldDTO> scientificFieldsDTO = Arrays.asList(scientificFieldDTO, scientificFieldDTO2);

        when(scientificFieldRepository.findAll()).thenReturn(scientificFields);
        when(scientificFieldConverter.entitiesToDTOs(scientificFields)).thenReturn(scientificFieldsDTO);

        List<ScientificFieldDTO> result = scientificFieldService.getAll();
        assertEquals(scientificFieldsDTO, result);
    }

    @Test
    void findByNameSuccessTest() throws Exception {
        when(scientificFieldRepository
                .findByScientificFieldName(scientificFieldDTO.getName()))
                .thenReturn(Optional.of(scientificField));

        ScientificFieldDTO result = scientificFieldService
                .findByName(scientificField.getScientificFieldName());

        assertNotNull(result);
        assertEquals(scientificFieldDTO, result);
    }

    @Test
    void findByNameFailureTest() throws Exception {
        when(scientificFieldRepository
                .findByScientificFieldName(scientificFieldDTO.getName()))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, ()->{
            scientificFieldService.findByName(scientificFieldDTO.getName());
        });
    }
}