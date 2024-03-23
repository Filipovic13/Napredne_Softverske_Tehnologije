package com.nst.domaci1.impl;

import com.nst.domaci1.converter.impl.EducationTitleConverter;
import com.nst.domaci1.domain.EducationTitle;
import com.nst.domaci1.dto.EducationTitleDTO;
import com.nst.domaci1.repository.EducationTitleRepository;
import com.nst.domaci1.service.EducationTitleService;
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
class EducationTitleServiceImplTest {

    @Autowired
    private EducationTitleService educationTitleService;

    @MockBean
    private EducationTitleRepository educationTitleRepository;

    @MockBean
    private EducationTitleConverter educationTitleConverter;

    private final EducationTitle educationTitle = new EducationTitle("#05","Honorary Degree");
    private final EducationTitle educationTitle2 = new EducationTitle("#04","Doctoral Degree");

    private final EducationTitleDTO dto = new EducationTitleDTO("#05","Honorary Degree");
    private final EducationTitleDTO dto2 = new EducationTitleDTO("#04","Doctoral Degree");

    @BeforeEach
    void setUp() {
        when(educationTitleConverter.toEntity(dto))
                .thenReturn(educationTitle);

        when(educationTitleConverter.toEntity(dto2))
                .thenReturn(educationTitle2);

        when(educationTitleConverter.toDTO(educationTitle))
                .thenReturn(dto);

        when(educationTitleConverter.toEntity(dto2))
                .thenReturn(educationTitle2);
    }

    @Test
    void saveSuccessTest() throws Exception {
        when(educationTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.empty());
        when(educationTitleRepository.findByEducationTitleName(dto.getName()))
                .thenReturn(Optional.empty());
        when(educationTitleRepository.save(educationTitle))
                .thenReturn(educationTitle);

        val result = educationTitleService.save(dto);
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void saveFailureTest() throws Exception {
        when(educationTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.of(educationTitle));
        Exception ex = assertThrows(Exception.class, ()-> {
            educationTitleService.save(dto);
        });
        assertEquals("Education Title with this ID - code already exists!", ex.getMessage());
    }

    @Test
    void saveFailure2Test() throws Exception {
        when(educationTitleRepository.findByEducationTitleName(dto.getName()))
                .thenReturn(Optional.of(educationTitle));
        Exception ex = assertThrows(Exception.class, ()-> {
            educationTitleService.save(dto);
        });
        assertEquals("Education Title with this name already exists!", ex.getMessage());
    }


    @Test
    void getAllSuccessTest()  {
        List<EducationTitle> list = Arrays.asList(educationTitle, educationTitle2);
        List<EducationTitleDTO> listDTO = Arrays.asList(dto, dto2);

        when(educationTitleConverter.entitiesToDTOs(list))
                .thenReturn(listDTO);
        when(educationTitleRepository.findAll()).thenReturn(list);

        val result = educationTitleService.getAll();
        assertEquals(listDTO, result);
    }

    @Test
    void deleteSuccessTest() throws Exception {
        when(educationTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.of(educationTitle));
        educationTitleService.delete(dto.getCode());
        verify(educationTitleRepository, times(1)).deleteById(educationTitle.getEducationTitleCode());
    }

    @Test
    void deleteFailureTest(){
        when(educationTitleRepository.findById(dto.getCode()))
                .thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, ()-> {
            educationTitleService.delete(dto.getCode());
        });
        assertEquals("Education Title  with the given ID - code doesn't exist!", ex.getMessage());
    }

    @Test
    void findByNameSuccessTest() throws Exception {
        when(educationTitleRepository.findByEducationTitleName(dto.getName()))
                .thenReturn(Optional.of(educationTitle));
        val result = educationTitleService.findByName(dto.getName());
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void findByNameFailureTest(){
        when(educationTitleRepository.findByEducationTitleName(educationTitle.getEducationTitleCode())).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, ()-> {
            educationTitleService.findByName(educationTitle.getEducationTitleName());
        });
        assertEquals("Education Title with the given name doesn't exist!", ex.getMessage());
    }
}