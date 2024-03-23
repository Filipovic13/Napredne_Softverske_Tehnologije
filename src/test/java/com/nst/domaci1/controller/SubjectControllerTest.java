package com.nst.domaci1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci1.converter.impl.SubjectConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.dto.SubjectChangeEspbDTO;
import com.nst.domaci1.dto.SubjectDTO;
import com.nst.domaci1.service.SubjectService;
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

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private SubjectConverter subjectConverter;

    private final Department department = new Department(
            "Katedra za softversko inzenjerstvo",
            "SI",
            1L,
            2L);


    private final SubjectDTO subjectDto = new SubjectDTO(
            1L,
            "Programiranje 1",
            4,
            department.getName());

    private final SubjectDTO subjectDto2 = new SubjectDTO(
            2L,
            "Programiranje 2",
            6,
            department.getName());


    @Test
    void saveSuccessTest() throws Exception {
        when(subjectService.save(subjectDto)).thenReturn(subjectDto);
        //System.out.println(subject);

        val JSONRequest = objectMapper.writeValueAsString(subjectDto);
        //System.out.println("JSONRequest: " + JSONRequest);

        val JSONResponse = mockMvc.perform(post("/subject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();
        //System.out.println("JSONResponse: "+ JSONResponse);

        val responseSubjectDto = objectMapper.readValue(JSONResponse, SubjectDTO.class);
        //System.out.println("responseSubjectDto: " + responseSubjectDto);

        assertEquals(subjectDto, responseSubjectDto);
    }


    @Test
    void saveConflictFailureTest() throws Exception {
        val JSONRequest = objectMapper.writeValueAsString(subjectDto);

        when(subjectService.save(subjectDto))
                .thenThrow(new Exception(
                        "Subject with the given name already exists!"
                ));

        val JSONResponse = mockMvc.perform(post("/subject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isConflict()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Subject with the given name already exists!", JSONResponse);
    }

    @Test
    void getAllSuccessTest() throws Exception {
        val list = Arrays.asList(subjectDto, subjectDto2);
        when(subjectService.getAll()).thenReturn(list);

        val JSONResponse = mockMvc.perform(get("/subject")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseType = new TypeReference<List<SubjectDTO>>() {
        };
        val responseList = objectMapper.readValue(JSONResponse, responseType);

        assertEquals(list, responseList);
    }


    @Test
    void deleteSuccessTest() throws Exception {
        doNothing().when(subjectService).delete(1L);

        mockMvc.perform(delete("/subject/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subjectService).delete(1L);
    }

    @Test
    void deleteFailureTest() throws Exception {

        doThrow(new Exception("Subject with the given ID doesn't exist!"))
                .when(subjectService).delete(33L);


        val JSONResponse = mockMvc.perform((delete("/subject/{id}", 33L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse()
                .getContentAsString();

        System.out.println("JSONResponse: " + JSONResponse);

        assertEquals(">>> Subject with the given ID doesn't exist!", JSONResponse);
    }

    @Test
    void updateSuccessTest() throws Exception {
        val subjectChangeEspbDTO = new SubjectChangeEspbDTO(5);

        val JSONRequest = objectMapper.writeValueAsString(subjectChangeEspbDTO);

        when(subjectService.updateESPB(1L, subjectChangeEspbDTO))
                .thenReturn(subjectDto);

        val JSONResponse = mockMvc.perform(patch("/subject/updateEspb/{subjectId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseSubjecDTO = objectMapper.readValue(JSONResponse,
                SubjectDTO.class);

        assertEquals(subjectDto, responseSubjecDTO);
    }

    @Test
    void updateFailureTest() throws Exception {

        val subjectChangeEspbDTO = new SubjectChangeEspbDTO(5);

        doThrow(new Exception("Subject with the given ID doesn't exist!"))
                .when(subjectService).updateESPB(33L, subjectChangeEspbDTO);

        val JSONRequest = objectMapper.writeValueAsString(subjectChangeEspbDTO);


        val JSONResponse = mockMvc.perform(patch("/subject/updateEspb/{subjectId}", 33L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Subject with the given ID doesn't exist!", JSONResponse);
    }

    @Test
    void findByIdSuccessTest() throws Exception {

        when(subjectService.findById(1L))
                .thenReturn(subjectDto);

        val JSONResponse = mockMvc.perform(get("/subject/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseSubjectDto = objectMapper.readValue(JSONResponse,
                SubjectDTO.class);

        assertEquals(subjectDto, responseSubjectDto);
    }

    @Test
    void findByIdFailureTest() throws Exception {

        doThrow(new Exception("Subject with the given ID doesn't exist!"))
                .when(subjectService).findById(33L);

        val JSONResponse = mockMvc.perform(get("/subject/{id}", 33L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();


        assertEquals(">>> Subject with the given ID doesn't exist!", JSONResponse);
    }

}