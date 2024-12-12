package com.nst.domaci1.impl;

import com.nst.domaci1.converter.impl.ManagementOfDepartmentHistoryConverter;
import com.nst.domaci1.domain.*;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.ManagementOfDepartmentHistoryRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ManagementOfDepartmentHistoryServiceImplTest {

    @Autowired
    private ManagementOfDepartmentHistoryService managementOfDepartmentHistoryService;

    @MockBean
    private ManagementOfDepartmentHistoryRepository managementOfDepartmentHistoryRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private ManagementOfDepartmentHistoryConverter managementOfDepartmentHistoryConverter;


    private final Department department = Department.builder().name("Katedra za matematiku").shortName("Mata").supervisorId(9L).secretaryId(12L).build();

    private final Member member = Member.builder().
            id(1L).
            firstName("Zika").lastName("Zikic").
            academicTitle(AcademicTitle.builder().academicTitleCode("#01").academicTitleName("Professor").build()).
            educationTitle(EducationTitle.builder().educationTitleCode("#04").educationTitleName("Doctoral Degree'").build()).
            scientificField(ScientificField.builder().scientificFieldCode("#03").scientificFieldName("Artificial Intelligence").build()).
            department(department).
            build();


    private final ManagementOfDepartmentHistory mngmnt = new ManagementOfDepartmentHistory(
            1L,
            LocalDate.of(2021, 4, 13),
            LocalDate.of(2022, 4, 13),
            ManagerRole.SECRETARY,
            member,
            department);

    private final ManagementOfDepartmentHistory mngmnt2 = new ManagementOfDepartmentHistory(
            2L,
            LocalDate.of(2023, 4, 13),
            null,
            ManagerRole.SUPERVISOR,
            member,
            department);

    private final ManagementOfDepartmentHistoryDTO dto = new ManagementOfDepartmentHistoryDTO(
            1L,
            LocalDate.of(2022, 6, 10),
            (LocalDate.of(2023, 6, 10)),
            "secretary",
            member.getId(),
            department.getName());

    private final ManagementOfDepartmentHistoryDTO dto2 = new ManagementOfDepartmentHistoryDTO(
            2L,
            LocalDate.of(2023, 4, 13),
            null,
            ManagerRole.SUPERVISOR.toString(),
            member.getId(),
            department.getName());

    @BeforeEach
    void setUp() {
        when(managementOfDepartmentHistoryConverter.toDTO(mngmnt))
                .thenReturn(dto);
        when(managementOfDepartmentHistoryConverter.toDTO(mngmnt2))
                .thenReturn(dto2);
        when(managementOfDepartmentHistoryConverter.toEntity(dto2))
                .thenReturn(mngmnt2);
    }

    @Test
    void findAllByDepartmentNameSuccessTest() throws Exception {
        List<ManagementOfDepartmentHistory> list = Arrays.asList(mngmnt, mngmnt2);
        List<ManagementOfDepartmentHistoryDTO> listDTO = Arrays.asList(dto, dto2);

        when(departmentRepository.findById(department.getName())).thenReturn(Optional.of(department));
        when(managementOfDepartmentHistoryRepository.findByDepartmentOrderByStartDateDesc(department)).thenReturn(list);
        when(managementOfDepartmentHistoryConverter.entitiesToDTOs(list)).thenReturn(listDTO);

        List<ManagementOfDepartmentHistoryDTO> result = managementOfDepartmentHistoryService.findAllByDepartmentName(department.getName());

        assertEquals(listDTO, result);
    }

    @Test
    void findAllByDepartmentNameFailureTest() throws Exception {
        when(departmentRepository.findById(department.getName())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.findAllByDepartmentName(department.getName());
        });
    }


    @Test
    void findAllByMemberSuccessTest() throws Exception {
        List<ManagementOfDepartmentHistory> list = Arrays.asList(mngmnt, mngmnt2);
        List<ManagementOfDepartmentHistoryDTO> listDTO = Arrays.asList(dto, dto2);

        when(memberRepository.findById(member.getId()))
                .thenReturn(Optional.of(member));
        when(managementOfDepartmentHistoryRepository
                .findByMemberOrderByStartDateDesc(member)).thenReturn(list);
        when(managementOfDepartmentHistoryConverter.entitiesToDTOs(list)).thenReturn(listDTO);

        List<ManagementOfDepartmentHistoryDTO> result = managementOfDepartmentHistoryService.findAllByMember(member.getId());
        assertEquals(listDTO, result);
    }

    @Test
    void findAllByMemberFailureTest() throws Exception {
        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.findAllByMember(member.getId());
        });
    }

    @Test
    void deleteByIdSuccessTest() throws Exception {
        when(managementOfDepartmentHistoryRepository.findById(mngmnt.getId())).thenReturn(Optional.of(mngmnt));
        managementOfDepartmentHistoryService.deleteById(mngmnt.getId());
        verify(managementOfDepartmentHistoryRepository, times(1)).delete(mngmnt);
    }

    @Test
    void deleteByIdFailureTest() {
        when(managementOfDepartmentHistoryRepository.findById(mngmnt.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.deleteById(mngmnt.getId());
        });
    }

    @Test
    void findByIdSuccessTest() throws Exception {
        when(managementOfDepartmentHistoryRepository.findById(mngmnt.getId())).thenReturn(Optional.of(mngmnt));
        ManagementOfDepartmentHistoryDTO result = managementOfDepartmentHistoryService.findById(mngmnt.getId());
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void findByIdFailureTest() throws Exception {
        when(managementOfDepartmentHistoryRepository.findById(mngmnt.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.findById(mngmnt.getId());
        });
    }

    @Test
    void getLatestMangerOfDepartmentSuccessLastEndDateNullTest() throws Exception {
        String role = "supervisor";
        when(departmentRepository.findById(department.getName())).thenReturn(Optional.of(department));
        when(managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleAndEndDateIsNullOrderByStartDateDesc(department, ManagerRole.SUPERVISOR)).thenReturn(Optional.of(mngmnt2));

        ManagementOfDepartmentHistoryDTO result = managementOfDepartmentHistoryService.getLatestMangerOfDepartment(department.getName(), role);

        assertNotNull(result);
        assertEquals(dto2, result);
    }

    @Test
    void getLatestMangerOfDepartmentSuccessLastEndDateNotNullTest() throws Exception {
        String role = "secretary";
        when(departmentRepository.findById(department.getName())).thenReturn(Optional.of(department));
        when(managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleAndEndDateIsNullOrderByStartDateDesc(department, ManagerRole.SECRETARY)).thenReturn(Optional.of(mngmnt));

        ManagementOfDepartmentHistoryDTO result = managementOfDepartmentHistoryService.getLatestMangerOfDepartment(department.getName(), role);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void getLatestMangerOfDepartmentFailureTest() throws Exception {
        when(departmentRepository.findById(department.getName())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.getLatestMangerOfDepartment(department.getName(), "secretary");
        });
    }

    @Test
    void getLatestMangerOfDepartmentFailure2Test() throws Exception {
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.getLatestMangerOfDepartment(department.getName(), "secretaryy");
        });
    }

    @Test
    void getLatestMangerOfDepartmentFailure3Test() throws Exception {
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.getLatestMangerOfDepartment(department.getName(), "supervisorr");
        });
    }

    @Test
    void getLatestMangerOfDepartmentFailure4Test() throws Exception {
        Department department1 = Department.builder().name("Katedra za softversko inzenjerstvo").shortName("SI").build();
        when(departmentRepository.findById(department1.getName())).thenReturn(Optional.of(department1));
        Exception ex = assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.getLatestMangerOfDepartment(department1.getName(), "supervisor");
        });
        assertEquals("There is no SUPERVISOR for department - " + department1.getName(), ex.getMessage());
    }

    @Test
    void saveSuccessTest() throws Exception {
        val newMngmnt = new ManagementOfDepartmentHistory(
                null,
                dto.getStartDate(),
                dto.getEndDate(),
                ManagerRole.SECRETARY,
                member,
                department
        );

        val newMngmntDTO = new ManagementOfDepartmentHistoryDTO(
                null,
                dto.getStartDate(),
                dto.getEndDate(),
                ManagerRole.SECRETARY.toString(),
                member.getId(),
                department.getName()
        );

        when(memberRepository.findById(member.getId()))
                .thenReturn(Optional.of(member));
        when(departmentRepository.findById(department.getName()))
                .thenReturn(Optional.of(department));
        when(managementOfDepartmentHistoryRepository.save(newMngmnt))
                .thenReturn(newMngmnt);
        when(managementOfDepartmentHistoryConverter.toDTO(newMngmnt))
                .thenReturn(newMngmntDTO);

        val result = managementOfDepartmentHistoryService.save(dto);
        assertNotNull(result);
        assertEquals(newMngmntDTO, result);
    }

    @Test
    void saveFailureTest() {
        val newDto = ManagementOfDepartmentHistoryDTO.builder().
                startDate(LocalDate.now())
                .endDate(LocalDate.now().minusDays(1)).
                build();
        Exception ex = assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.save(newDto);
        });
        assertEquals("End date must be after start date!", ex.getMessage());
    }

    @Test
    void updateSuccessTest() throws Exception {
        when(managementOfDepartmentHistoryRepository.findById(mngmnt2.getId())).thenReturn(Optional.of(mngmnt2));
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(departmentRepository.findById(department.getName())).thenReturn(Optional.of(department));

        mngmnt2.setStartDate(dto.getStartDate());
        mngmnt2.setEndDate(dto.getEndDate());
        mngmnt2.setManagerRole(ManagerRole.SECRETARY);

        when(managementOfDepartmentHistoryRepository.save(mngmnt2))
                .thenReturn(mngmnt2);

        val result = managementOfDepartmentHistoryService.update(mngmnt2.getId(), dto2);

        assertNotNull(result);
        assertEquals(dto2, result);
    }

    @Test
    void updateFailureTest() {
        when(managementOfDepartmentHistoryRepository.findById(mngmnt2.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            managementOfDepartmentHistoryService.update(mngmnt2.getId(), dto);
        });
    }
}