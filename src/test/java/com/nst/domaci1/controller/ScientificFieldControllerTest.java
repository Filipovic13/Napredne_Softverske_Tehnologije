package com.nst.domaci1.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci1.dto.ScientificFieldDTO;
import com.nst.domaci1.service.ScientificFieldService;
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

@WebMvcTest(ScientificFieldController.class)
class ScientificFieldControllerTest {

    private final String url = "/scientificField";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScientificFieldService scientificFieldService;

    private final ScientificFieldDTO dto = new ScientificFieldDTO("#01", "Algorithms and Data Structures");

    private final ScientificFieldDTO dto2 = new ScientificFieldDTO("#02", "Software Testing and Quality Assurance");

    @Test
    void saveSuccessTest() throws Exception {

        when(scientificFieldService.save(dto))
                .thenReturn(dto);

        val JSONRequest = objectMapper.writeValueAsString(dto);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();

        val responseScientificFieldDTO = objectMapper.readValue(JSONResponse,
                ScientificFieldDTO.class);

        assertEquals(dto, responseScientificFieldDTO);
    }

    @Test
    void saveFailureTest() throws Exception {

        doThrow(new Exception("Scientific Field with this ID - code already exists!"))
                .when(scientificFieldService).save(dto);

        val JSONRequest = objectMapper.writeValueAsString(dto);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isConflict()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Scientific Field with this ID - code already exists!", JSONResponse);
    }

    @Test
    void getAllSuccessTest() throws Exception {

        List<ScientificFieldDTO> lisDTO = Arrays.asList(dto, dto2);

        when(scientificFieldService.getAll())
                .thenReturn(lisDTO);


        val JSONResponse = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseScientificFieldDTO = objectMapper.readValue(JSONResponse,
                new TypeReference<List<ScientificFieldDTO>>() {});

        assertEquals(lisDTO, responseScientificFieldDTO);
    }

    @Test
    void deleteSuccessTest() throws Exception {
        doNothing().when(scientificFieldService)
                .delete(dto.getCode());


        val JSONResponse = mockMvc.perform(delete(url + "/{code}", dto.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        assertEquals("Scientific Field successfully removed!", JSONResponse);
    }

    @Test
    void deleteFailureTest() throws Exception {

        doThrow(new Exception("Scientific Field with the given ID - code doesn't exist!"))
                .when(scientificFieldService).delete("nepostojece");


        val JSONResponse = mockMvc.perform(delete(url + "/{code}", "nepostojece")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Scientific Field with the given ID - code doesn't exist!", JSONResponse);
    }


    @Test
    void findByNameSuccessTest() throws Exception {

        when(scientificFieldService.findByName(dto.getName()))
                .thenReturn(dto);


        val JSONResponse = mockMvc.perform(get(url + "/{scientificField}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();


        val responseScientificFieldDTO = objectMapper.readValue(JSONResponse,
                ScientificFieldDTO.class);

        assertEquals(dto, responseScientificFieldDTO);

    }

    @Test
    void findByNameFailureTest() throws Exception {
        doThrow(new Exception("Scientific Filed with the given name doesn't exist!"))
                .when(scientificFieldService).findByName("nepostojece");

        val JSONResponse = mockMvc.perform(get(url + "/{scientificField}", "nepostojece")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Scientific Filed with the given name doesn't exist!", JSONResponse);

    }

}