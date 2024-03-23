package com.nst.domaci1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.dto.DepartmentSetManagerDTO;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import com.nst.domaci1.service.DepartmentService;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
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

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    private final String url = "/department";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private ManagementOfDepartmentHistoryService managementOfDepartmentHistoryService;


    private final DepartmentDTO dto = new DepartmentDTO(
            "Katedra za matematiku",
            "Mata",
            1L,
            2L
    );

    private final DepartmentDTO dto2 = new DepartmentDTO(
            "Katedra za softversko inzenjerstvo",
            "SI",
            3L,
            4L
    );

    private final ManagementOfDepartmentHistoryDTO mngmntDTO = new ManagementOfDepartmentHistoryDTO(
            1L,
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2022, 1, 1),
            "Supervisor",
            1L,
            dto.getName()
    );

    private final ManagementOfDepartmentHistoryDTO mngmntDTO2 = new ManagementOfDepartmentHistoryDTO(
            2L,
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2023, 1, 1),
            "Secretary",
            1L,
            dto.getName()
    );

    @Test
    void saveSuccessTest() throws Exception {

        when(departmentService.save(dto))
                .thenReturn(dto);

        val JSONRequest = objectMapper.writeValueAsString(dto);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                DepartmentDTO.class);

        assertEquals(dto, responseDTO);
    }

    @Test
    void saveFailureTest() throws Exception {

        doThrow(new Exception("Department with the given name already exists!"))
                .when(departmentService).save(dto);

        val JSONRequest = objectMapper.writeValueAsString(dto);

        val JSONResponse = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isConflict()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Department with the given name already exists!", JSONResponse);
    }


    @Test
    void getAllTest() throws Exception {
        List<DepartmentDTO> listDTO = Arrays.asList(dto, dto2);

        when(departmentService.getAll())
                .thenReturn(listDTO);

        val JSONResponse = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseList = objectMapper.readValue(JSONResponse,
                new TypeReference<List<DepartmentDTO>>() {
                });

        assertEquals(listDTO, responseList);
    }

    @Test
    void deleteSuccessTest() throws Exception {
        doNothing().when(departmentService).delete(dto.getName());

        val JSONResponse = mockMvc.perform(delete(url + "/{name}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();


        assertEquals("Department successfully removed!", JSONResponse);
    }


    @Test
    void deleteFailureTest() throws Exception {
        doThrow(new Exception("Department with the given name doesn't exist! " +
                "\nEnter one of next values: \n"))
                .when(departmentService).delete("Nepostojece ime");

        val JSONResponse = mockMvc.perform(delete(url + "/{memberId}", "Nepostojece ime")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();


        assertEquals(">>> Department with the given name doesn't exist! \nEnter one of next values: \n", JSONResponse);
    }


    @Test
    void findByIdSuccessTest() throws Exception {

        when(departmentService.findById(dto.getName())).
                thenReturn(dto);

        val JSONResponse = mockMvc.perform(get(url + "/{name}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                DepartmentDTO.class);

        assertEquals(dto, responseDTO);
    }


    @Test
    void findByIdFailureTest() throws Exception {
        doThrow(new Exception("Department with the given name doesn't exist!"))
                .when(departmentService).findById(dto.getName());

        val JSONResponse = mockMvc.perform(get(url + "/{memberId}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Department with the given name doesn't exist!", JSONResponse);
    }

    @Test
    void setManagerForDepartmentSuccessTest() throws Exception {

        val changesDTO = new DepartmentSetManagerDTO(
                "supervisor", 33L
        );


        when(departmentService.setManagerForDepartment(dto.getName(), changesDTO))
                .thenReturn(dto);

        val JSONRequest = objectMapper.writeValueAsString(changesDTO);

        val JSONResponse = mockMvc.perform(patch(url + "/setCurrentManager/{departmentName}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                DepartmentDTO.class);

        assertEquals(dto, responseDTO);
    }

    /////////////////////////////////////////////////

    @Test
    public void findAllManagersByDepartmentNameSuccessTest() throws Exception {
        List<ManagementOfDepartmentHistoryDTO> list = Arrays.asList(mngmntDTO, mngmntDTO2);

        when(managementOfDepartmentHistoryService.findAllByDepartmentName(mngmntDTO.getDepartmentName()))
                .thenReturn(list);

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/managersByDepartment/{departmentName}", dto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseListDTO = objectMapper.readValue(JSONResponse,
                new TypeReference<List<ManagementOfDepartmentHistoryDTO>>() {
                });

        assertEquals(list, responseListDTO);
    }

    @Test
    public void findAllManagersByDepartmentNameFailureTest() throws Exception {

        doThrow(new Exception("Department with the given name doesn't exist! " +
                "\nEnter one of next values: \n"))
                .when(managementOfDepartmentHistoryService).findAllByDepartmentName("nesto");

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/managersByDepartment/{departmentName}", "nesto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Department with the given name doesn't exist! " +
                "\nEnter one of next values: \n", JSONResponse);
    }


    @Test
    void findAllManagementHistoryByMemberIdSuccessTest() throws Exception {
        List<ManagementOfDepartmentHistoryDTO> list = Arrays.asList(mngmntDTO, mngmntDTO2);

        when(managementOfDepartmentHistoryService.findAllByMember(mngmntDTO.getMemberId()))
                .thenReturn(list);

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/historyByMember/{memberId}", mngmntDTO.getMemberId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseListDTO = objectMapper.readValue(JSONResponse,
                new TypeReference<List<ManagementOfDepartmentHistoryDTO>>() {
                });

        assertEquals(list, responseListDTO);
    }

    @Test
    void findAllManagementHistoryByMemberIdFailureTest() throws Exception {

        doThrow(new Exception("Member with the given ID doesn't exist!"))
                .when(managementOfDepartmentHistoryService).findAllByMember(0L);

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/historyByMember/{memberId}", 0L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Member with the given ID doesn't exist!", JSONResponse);
    }

    @Test
    public void deleteByIdManagementOfDepartmentHistorySuccessTest() throws Exception {
        doNothing()
                .when(managementOfDepartmentHistoryService)
                .deleteById(mngmntDTO.getId());


        val JSONResponse = mockMvc.perform(delete(url + "/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}", mngmntDTO.getMemberId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        assertEquals("Management Of Department History successfully removed!", JSONResponse);
    }

    @Test
    public void deleteByIdManagementOfDepartmentHistoryFailureTest() throws Exception {
        doThrow(new Exception("Management Of DepartmentHistory with the given ID doesn't exist!"))
                .when(managementOfDepartmentHistoryService).deleteById(0L);


        val JSONResponse = mockMvc.perform(delete(url + "/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}", 0L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Management Of DepartmentHistory with the given ID doesn't exist!", JSONResponse);
    }


    @Test
    public void findByIdManagementOfDepartmentHistorySuccessTest() throws Exception {
        when(managementOfDepartmentHistoryService
                .findById(mngmntDTO.getMemberId()))
                .thenReturn(mngmntDTO);

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}", mngmntDTO.getMemberId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                ManagementOfDepartmentHistoryDTO.class);

        assertEquals(mngmntDTO, responseDTO);
    }

    @Test
    public void findByIdManagementOfDepartmentHistoryFailureTest() throws Exception {

        doThrow(new Exception("Management Of DepartmentHistory with the given ID doesn't exist!"))
                .when(managementOfDepartmentHistoryService)
                .findById(mngmntDTO.getId());

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}", mngmntDTO.getMemberId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();
        System.out.println("JSONResponse: " + JSONResponse);

        assertEquals(">>> Management Of DepartmentHistory with the given ID doesn't exist!", JSONResponse);
    }


    @Test
    public void getLatestManagerForDepartmentSuccessTest() throws Exception {
        when(managementOfDepartmentHistoryService
                .getLatestMangerOfDepartment(mngmntDTO2.getDepartmentName(), "Secretary"))
                .thenReturn(mngmntDTO2);

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/lastManager/{departmentName}/{managerRole}", mngmntDTO2.getDepartmentName(), "Secretary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                ManagementOfDepartmentHistoryDTO.class);

        assertEquals(mngmntDTO2, responseDTO);
    }

    @Test
    public void getLatestManagerForDepartmentFailureTest() throws Exception {

        doThrow(new Exception("Department with the given name doesn't exist! " +
                "\nEnter one of next values: \n"))
                .when(managementOfDepartmentHistoryService)
                .getLatestMangerOfDepartment("Neki department", "Secretary");

        val JSONResponse = mockMvc.perform(get(url + "/managementOfDepartmentHistory/lastManager/{departmentName}/{managerRole}", "Neki department", "Secretary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();
        System.out.println("JSONResponse: " + JSONResponse);

        assertEquals(">>> Department with the given name doesn't exist! " +
                "\nEnter one of next values: \n", JSONResponse);
    }


    @Test
    void updateManagementOfDepartmentHistorySuccessTest() throws Exception {

        when(managementOfDepartmentHistoryService.update(mngmntDTO.getId(), mngmntDTO))
                .thenReturn(mngmntDTO);

        val JSONRequest = objectMapper.writeValueAsString(mngmntDTO);

        val JSONResponse = mockMvc.perform(put(url + "/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}", mngmntDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                ManagementOfDepartmentHistoryDTO.class);

        assertEquals(mngmntDTO, responseDTO);
    }


    @Test
    void updateManagementOfDepartmentHistoryFailureTest() throws Exception {

        doThrow(new Exception("Management Of Department History with the given ID doesn't exist!"))
                .when(managementOfDepartmentHistoryService)
                .update(0L, mngmntDTO);

        val JSONRequest = objectMapper.writeValueAsString(mngmntDTO);

        val JSONResponse = mockMvc.perform(put(url + "/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Management Of Department History with the given ID doesn't exist!", JSONResponse);
    }


    @Test
    void createManagementOfDepartmentHistorySuccessTest() throws Exception {

        when(managementOfDepartmentHistoryService.save(mngmntDTO))
                .thenReturn(mngmntDTO);

        val JSONRequest = objectMapper.writeValueAsString(mngmntDTO);

        val JSONResponse = mockMvc.perform(post(url + "/managementOfDepartmentHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getContentAsString();

        val responseDTO = objectMapper.readValue(JSONResponse,
                ManagementOfDepartmentHistoryDTO.class);

        assertEquals(mngmntDTO, responseDTO);
    }

    @Test
    void createManagementOfDepartmentHistoryFailureTest() throws Exception {

        doThrow(new Exception("Member with the given ID doesn't exist!"))
                .when(managementOfDepartmentHistoryService)
                .save(mngmntDTO);

        val JSONRequest = objectMapper.writeValueAsString(mngmntDTO);
        System.out.println(mngmntDTO);

        val JSONResponse = mockMvc.perform(post(url + "/managementOfDepartmentHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONRequest))
                .andExpect(status().isBadRequest()).andReturn().getResponse()
                .getContentAsString();

        assertEquals(">>> Member with the given ID doesn't exist!", JSONResponse);
    }

}