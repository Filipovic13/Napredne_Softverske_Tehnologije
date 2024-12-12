package com.nst.domaci1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import com.nst.domaci1.dto.MemberChangeAllScienceFieldsDTO;
import com.nst.domaci1.dto.MemberChangeDepartmentDTO;
import com.nst.domaci1.dto.MemberDTO;
import com.nst.domaci1.service.AcademicTitleHistoryService;
import com.nst.domaci1.service.MemberService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    private final String url = "/member";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private AcademicTitleHistoryService academicTitleHistoryService;

    private final MemberDTO memberDTO = new MemberDTO(
            1L,
            "Zika",
            "Zikic",
            "Professor",
            "Doctoral Degree",
            "Software Testing and Quality Assurance",
            "Katedra za softversko inzenjerstvo"
    );

    private final MemberDTO memberDTO2 = new MemberDTO(
            2L,
            "Pera",
            "Peric",
            "Graduate Assistant",
            "Associate Degree",
            "Artificial Intelligence",
            "Katedra za softversko inzenjerstvo"
    );


    private final AcademicTitleHistoryDTO athDTO = new AcademicTitleHistoryDTO(
            1L,
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2023, 1, 1),
            "Professor",
            "Software Testing and Quality Assurance",
            1L
    );

    private final AcademicTitleHistoryDTO athDTO2 = new AcademicTitleHistoryDTO(
            2L,
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2022, 1, 1),
            "Graduate Assistant",
            "Katedra za softversko inzenjerstvo",
            1L
    );

    private final AcademicTitleHistoryDTO athDTO3 = new AcademicTitleHistoryDTO(
            2L,
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2022, 1, 1),
            "Academic Advisor",
            "Katedra za softversko inzenjerstvo",
            2L
    );

    @Test
    void saveSuccessTest() throws Exception {
        when(memberService.save(memberDTO))
                .thenReturn(memberDTO);

        val JSONRequest = objectMapper.writeValueAsString(memberDTO);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();

        val responseMemberDTO = objectMapper.readValue(JSONResponse,
                MemberDTO.class);

        assertEquals(memberDTO, responseMemberDTO);
    }

    @Test
    void saveFailureTest() throws Exception {

        doThrow(new Exception("Scientific Field doesn't exist!\n " +
                "Enter one of the values that exist in database: \n []"))
                .when(memberService)
                .save(memberDTO);

        val JSONRequest = objectMapper.writeValueAsString(memberDTO);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isConflict()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Scientific Field doesn't exist!\n" +
                " Enter one of the values that exist in database: \n []", JSONResponse);
    }

    @Test
    void getAllTest() throws Exception {
        List<MemberDTO> listDTO = Arrays.asList(memberDTO, memberDTO2);

        when(memberService.getAll())
                .thenReturn(listDTO);

        val JSONResponse = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseList = objectMapper.readValue(JSONResponse,
                new TypeReference<List<MemberDTO>>() {
                });

        assertEquals(listDTO, responseList);
    }

//    @Test
//    void getAllByPage() throws Exception {
//
//        List<MemberDTO> listDTO = Arrays.asList(memberDTO, memberDTO2);
//        Page<MemberDTO> membersDTOPage = new PageImpl<>(listDTO);
//
//        int page = 0;
//        int pageSize = 2;
//        String sortBy = "lastName";
//        String sortDirection = "asc";
//
//        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).ascending());
//
//        when(memberService.getAll(pageable)).thenReturn(membersDTOPage);
//
//        val JSONResponse = mockMvc.perform(get(url + "/paging")
//                        .param("page", String.valueOf(page))
//                        .param("pageSize", String.valueOf(pageSize))
//                        .param("sortBy", sortBy)
//                        .param("sortDirection", sortDirection)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn().getResponse()
//                .getContentAsString();
//
//        val jsonResponse = objectMapper.readValue(JSONResponse,
//                new TypeReference<Page<MemberDTO>>() {
//                });
//
//        assertEquals(membersDTOPage, jsonResponse);
//    }


    @Test
    void deleteSuccessTest() throws Exception {
        doNothing().when(memberService).delete(1L);

        val JSONResponse = mockMvc.perform(delete(url + "/{memberId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();


        assertEquals("Member successfully removed!", JSONResponse);
    }


    @Test
    void deleteFailureTest() throws Exception {
        doThrow(new Exception("Member with the given ID doesn't exist!"))
                .when(memberService).delete(33L);

        val JSONResponse = mockMvc.perform(delete(url + "/{memberId}", 33L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();


        assertEquals(">>> Member with the given ID doesn't exist!", JSONResponse);
    }

    @Test
    void updateScienceFieldsSuccessTest() throws Exception {
        val changesDTO = new MemberChangeAllScienceFieldsDTO(
                "Lecturer",
                "Bachelor Degree",
                "Business Analysis"
        );


        val JSONRequest = objectMapper.writeValueAsString(changesDTO);

        when(memberService.updateScienceFields(2L, changesDTO)).
                thenReturn(memberDTO2);

        val JSONResponse = mockMvc.perform(patch(url + "/updateScienceFields/{memberId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseChangesDTO = objectMapper.readValue(JSONResponse,
                MemberDTO.class);

        assertEquals(memberDTO2, responseChangesDTO);
    }


    @Test
    void updateScienceFieldsFailureTest() throws Exception {
        val changesDTO = new MemberChangeAllScienceFieldsDTO(
                "Lecturer",
                "Bachelor Degree",
                "Business Analysis"
        );

        val JSONRequest = objectMapper.writeValueAsString(changesDTO);

        doThrow(new Exception("Member with the given ID doesn't exist!"))
                .when(memberService).updateScienceFields(33L, changesDTO);

        val JSONResponse = mockMvc.perform(patch(url + "/updateScienceFields/{memberId}", 33L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Member with the given ID doesn't exist!", JSONResponse);
    }

    @Test
    void updateDepartmentSuccessTest() throws Exception {
        val changesDTO = new MemberChangeDepartmentDTO(
                "Katedra za matematiku"
        );


        val JSONRequest = objectMapper.writeValueAsString(changesDTO);

        when(memberService.updateDepartment(2L, changesDTO)).
                thenReturn(memberDTO2);

        val JSONResponse = mockMvc.perform(patch(url + "/updateDepartment/{memberId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseChangesDTO = objectMapper.readValue(JSONResponse,
                MemberDTO.class);

        assertEquals(memberDTO2, responseChangesDTO);
    }


    @Test
    void updateDepartmentFailureTest() throws Exception {
        val changesDTO = new MemberChangeDepartmentDTO(
                "Katedra za matematiku"
        );

        val JSONRequest = objectMapper.writeValueAsString(changesDTO);

        doThrow(new Exception("Member with the given ID doesn't exist!"))
                .when(memberService).updateDepartment(33L, changesDTO);

        val JSONResponse = mockMvc.perform(patch(url + "/updateDepartment/{memberId}", 33L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Member with the given ID doesn't exist!", JSONResponse);
    }

    @Test
    void findByIdSuccessTest() throws Exception {


        when(memberService.findById(2L)).
                thenReturn(memberDTO2);

        val JSONResponse = mockMvc.perform(get(url + "/{memberId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseChangesDTO = objectMapper.readValue(JSONResponse,
                MemberDTO.class);

        assertEquals(memberDTO2, responseChangesDTO);
    }


    @Test
    void findByIdFailureTest() throws Exception {
        doThrow(new Exception("Member with the given ID doesn't exist!"))
                .when(memberService).findById(33L);

        val JSONResponse = mockMvc.perform(get(url + "/{memberId}", 33L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Member with the given ID doesn't exist!", JSONResponse);
    }

    @Test
    void getAllAcademicTitleHistoriesTest() throws Exception {
        List<AcademicTitleHistoryDTO> listDTO = Arrays.asList(athDTO, athDTO2, athDTO3);

        when(academicTitleHistoryService.getAll())
                .thenReturn(listDTO);

        val JSONResponse = mockMvc.perform(get(url + "/academicTitleHistories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseList = objectMapper.readValue(JSONResponse,
                new TypeReference<List<AcademicTitleHistoryDTO>>() {
                });

        assertEquals(listDTO, responseList);
    }


    @Test
    void findAllAcademicTitleHistoriesByMemberTest() throws Exception {
        List<AcademicTitleHistoryDTO> listDTO = Arrays.asList(athDTO, athDTO2);

        when(academicTitleHistoryService.findAllByMemberId(2L))
                .thenReturn(listDTO);

        val JSONResponse = mockMvc.perform(get(url + "/{memberId}/academicTitleHistories", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseList = objectMapper.readValue(JSONResponse,
                new TypeReference<List<AcademicTitleHistoryDTO>>() {
                });

        assertEquals(listDTO, responseList);
    }

    @Test
    void saveAcademicTitleHistorySuccessTest() throws Exception {
        when(academicTitleHistoryService.save(athDTO))
                .thenReturn(athDTO);

        val JSONRequest = objectMapper.writeValueAsString(athDTO);

        val JSONResponse = mockMvc.perform(post(url + "/academicTitleHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                AcademicTitleHistoryDTO.class);

        assertEquals(athDTO, responseDTO);
    }

    @Test
    void saveAcademicTitleHistoryFailureTest() throws Exception {

        val badDateDTO = new AcademicTitleHistoryDTO(
                1L,
                LocalDate.now(),
                LocalDate.now().minusDays(1),
                "Professor",
                "Business Analysis",
                1L
        );

        doThrow(new Exception("End end must be after start date!"))
                .when(academicTitleHistoryService)
                .save(badDateDTO);

        val JSONRequest = objectMapper.writeValueAsString(badDateDTO);

        val JSONResponse = mockMvc.perform(post(url + "/academicTitleHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isConflict()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> End end must be after start date!", JSONResponse);
    }

    @Test
    void updateAcademicTitleHistorySuccessTest() throws Exception {
        when(academicTitleHistoryService.updateAcademicTitleHistory(1L, athDTO))
                .thenReturn(athDTO);

        val JSONRequest = objectMapper.writeValueAsString(athDTO);

        val JSONResponse = mockMvc.perform(put(url + "/academicTitleHistory/{academicTitleHistoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                AcademicTitleHistoryDTO.class);

        assertEquals(athDTO, responseDTO);
    }

    @Test
    void updateAcademicTitleHistoryFailureTest() throws Exception {

        doThrow(new Exception("Member with the given ID does not exist!"))
                .when(academicTitleHistoryService)
                .updateAcademicTitleHistory(1L, athDTO);

        val JSONRequest = objectMapper.writeValueAsString(athDTO);

        val JSONResponse = mockMvc.perform(put(url + "/academicTitleHistory/{academicTitleHistoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Member with the given ID does not exist!", JSONResponse);
    }

    @Test
    void deleteAcademicTitleHistorySuccessTest() throws Exception {
        doNothing().when(academicTitleHistoryService)
                .delete(1L);

        val JSONResponse = mockMvc.perform(delete(url + "/academicTitle/{academicTitleId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        assertEquals("Academic Title History successfully removed!", JSONResponse);
    }

    @Test
    void deleteAcademicTitleHistoryFailureTest() throws Exception {

        doThrow(new Exception("Member with the given ID does not exist!"))
                .when(academicTitleHistoryService)
                .delete(1L);

        val JSONRequest = objectMapper.writeValueAsString(athDTO);

        val JSONResponse = mockMvc.perform(delete(url + "/academicTitle/{academicTitleId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Member with the given ID does not exist!", JSONResponse);
    }

}