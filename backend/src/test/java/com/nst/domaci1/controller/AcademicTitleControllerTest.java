package com.nst.domaci1.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci1.dto.AcademicTitleDTO;
import com.nst.domaci1.service.AcademicTitleService;
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

@WebMvcTest(AcademicTitleController.class)
class AcademicTitleControllerTest {

    String url = "/academicTitle";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AcademicTitleService academicTitleService;


    private AcademicTitleDTO dto = new AcademicTitleDTO("#01", "Professor");

    private AcademicTitleDTO dto2 = new AcademicTitleDTO("#02", "Adjunct Professor");


    @Test
    public void saveSuccessTest() throws Exception {

        when(academicTitleService.save(dto))
                .thenReturn(dto);

        val JSONRequest = objectMapper.writeValueAsString(dto);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();

        val responseAcademicTitleDto = objectMapper
                .readValue(JSONResponse, AcademicTitleDTO.class);

        assertEquals(dto, responseAcademicTitleDto);
    }

    @Test
    public void saveFailureTest() throws Exception {

        when(academicTitleService.save(dto))
                .thenThrow(new Exception("Academic Title with this ID - code already exists!"));


        val JSONRequest = objectMapper.writeValueAsString(dto);
        System.out.println("JSONRequest: " + JSONRequest);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isConflict()).andReturn().getResponse()
                .getContentAsString();
        System.out.println("JSONResponse: " + JSONResponse);


        assertEquals(">>> Academic Title with this ID - code already exists!", JSONResponse);
    }


    @Test
    public void getAllSuccessTest() throws Exception {

        List<AcademicTitleDTO> listDTO = Arrays.asList(dto, dto2);

        when(academicTitleService.getAll())
                .thenReturn(listDTO);


        val JSONResponse = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseAcademicTitleDto = objectMapper
                .readValue(JSONResponse,
                        new TypeReference<List<AcademicTitleDTO>>() {
                        });

        assertEquals(listDTO, responseAcademicTitleDto);
    }

    @Test
    public void deleteSuccessTest() throws Exception {
        doNothing()
                .when(academicTitleService)
                .delete(dto.getCode());


        val JSONResponse = mockMvc.perform(delete(url + "/{code}", dto.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        assertEquals("Academic Title successfully removed!", JSONResponse);
    }

    @Test
    public void deleteFailureTest() throws Exception {
        doThrow(new Exception("Academic Title with the given name doesn't exist!"))
                .when(academicTitleService).delete("-1");


        val JSONResponse = mockMvc.perform(delete(url + "/{code}", "-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Academic Title with the given name doesn't exist!", JSONResponse);
    }

    @Test
    public void findByNameSuccessTest() throws Exception {
        when(academicTitleService.findByName(dto.getName()))
                .thenReturn(dto);

        val JSONResponse = mockMvc.perform(get(url + "/{academicTitle}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseAcademicTitleDTO = objectMapper.readValue(JSONResponse,
                AcademicTitleDTO.class);

        assertEquals(dto, responseAcademicTitleDTO);
    }

    @Test
    public void findByNameFailureTest() throws Exception {

        doThrow(new Exception("Academic Title with the given name doesn't exist!"))
                .when(academicTitleService).findByName("nesto");

        val JSONResponse = mockMvc.perform(get(url + "/{academicTitle}", "nesto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();
        System.out.println("JSONResponse: " + JSONResponse);

        assertEquals(">>> Academic Title with the given name doesn't exist!", JSONResponse);
    }
}