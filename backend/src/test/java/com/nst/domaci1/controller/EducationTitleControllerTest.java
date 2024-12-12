package com.nst.domaci1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci1.dto.EducationTitleDTO;
import com.nst.domaci1.service.EducationTitleService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EducationTitleController.class)
class EducationTitleControllerTest {

    private final String url = "/educationTitle";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EducationTitleService educationTitleService;

    private final EducationTitleDTO dto = new EducationTitleDTO("#01", "Bachelor Degree");

    private final EducationTitleDTO dto2 = new EducationTitleDTO("#02", "Master Degree");


    @Test
    void saveSuccessTest() throws Exception {
        when(educationTitleService.save(dto))
                .thenReturn(dto);

        val JSONRequest = objectMapper.writeValueAsString(dto);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                EducationTitleDTO.class);

        assertEquals(dto, responseDTO);
    }

    @Test
    void saveFailureTest() throws Exception {

        doThrow(new Exception("Education Title with this ID - code already exists!"))
                .when(educationTitleService).save(dto);

        val JSONRequest = objectMapper.writeValueAsString(dto);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isConflict()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Education Title with this ID - code already exists!", JSONResponse);
    }

    @Test
    void getAllSuccessTest() throws Exception {
        List<EducationTitleDTO> list = Arrays.asList(dto, dto2);

        when(educationTitleService.getAll())
                .thenReturn(list);


        val JSONResponse = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseList = objectMapper.readValue(JSONResponse,
                new TypeReference<List<EducationTitleDTO>>() {
                });

        assertEquals(list, responseList);
    }


    @Test
    void deleteSuccessTest() throws Exception {
        doNothing().when(educationTitleService).delete("#1");


        val JSONResponse = mockMvc.perform(delete(url + "/{code}", "#1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        assertEquals("Education Title successfully removed!", JSONResponse);
    }

    @Test
    void deleteFailureTest() throws Exception {

        doThrow(new Exception("Education Title  with the given ID - code doesn't exist!"))
                .when(educationTitleService).delete("#333");

        val JSONResponse = mockMvc.perform(delete(url + "/{code}", "#333")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Education Title  with the given ID - code doesn't exist!", JSONResponse);
    }


    @Test
    void findByNameSuccessTest() throws Exception {
        when(educationTitleService.findByName(dto.getName()))
                .thenReturn(dto);

        val JSONResponse = mockMvc.perform(get(url + "/{educationTitle}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                EducationTitleDTO.class);

        assertEquals(dto, responseDTO);
    }

    @Test
    void findByNameFailureTest() throws Exception {

        doThrow(new Exception("Education Title with the given name doesn't exist!"))
                .when(educationTitleService).findByName(dto.getName());

        val JSONResponse = mockMvc.perform(get(url + "/{educationTitle}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Education Title with the given name doesn't exist!", JSONResponse);
    }


}