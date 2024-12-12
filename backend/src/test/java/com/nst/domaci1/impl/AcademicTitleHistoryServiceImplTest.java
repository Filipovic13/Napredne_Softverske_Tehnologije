package com.nst.domaci1.impl;

import com.nst.domaci1.converter.impl.AcademicTitleHistoryConverter;
import com.nst.domaci1.domain.*;
import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import com.nst.domaci1.repository.AcademicTitleHistoryRepository;
import com.nst.domaci1.repository.AcademicTitleRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.repository.ScientificFieldRepository;
import com.nst.domaci1.service.AcademicTitleHistoryService;
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
class AcademicTitleHistoryServiceImplTest {

    @Autowired
    private AcademicTitleHistoryService academicTitleHistoryService;

    @MockBean
    private AcademicTitleHistoryRepository academicTitleHistoryRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private AcademicTitleRepository academicTitleRepository;

    @MockBean
    private ScientificFieldRepository scientificFieldRepository;

    @MockBean
    private AcademicTitleHistoryConverter academicTitleHistoryConverter;


    private final AcademicTitle at = new AcademicTitle("#01", "Professor");
    private final EducationTitle et = new EducationTitle("#04", "Doctoral Degree");
    private final ScientificField sf = new ScientificField("#03", "Artificial Intelligence");
    private final Department department = new Department("Katedra za softversko inzenjerstvo", "SI", 1L, 2L);
    private final Member member = new Member(
            1L,
            "Zika",
            "Zikic",
            at,
            et,
            sf,
            department
    );

    private AcademicTitleHistory academicTitleHistory;

    private final AcademicTitleHistory academicTitleHistory2 = AcademicTitleHistory.builder().
            id(null).
            startDate(LocalDate.of(2022, 4, 10)).
            endDate(LocalDate.of(2023, 4, 10)).
            academicTitle(at).
            scientificField(sf).
            member(member).
            build();

    private final AcademicTitleHistoryDTO dto = new AcademicTitleHistoryDTO(
            1L,
            LocalDate.of(2022, 4, 10),
            LocalDate.of(2023, 4, 10),
            at.getAcademicTitleName(),
            sf.getScientificFieldName(),
            member.getId()
    );

    private final AcademicTitleHistoryDTO dto2 = AcademicTitleHistoryDTO.builder().
            id(null).
            startDate(LocalDate.of(2022, 4, 10)).
            endDate(LocalDate.of(2023, 4, 10)).
            academicTitle(at.getAcademicTitleName()).
            scientificField(sf.getScientificFieldName()).
            memberId(member.getId()).
            build();


    @BeforeEach
    void setUp() {

        academicTitleHistory = AcademicTitleHistory.builder().
                id(1L).
                startDate(LocalDate.of(2021, 4, 10)).
                endDate(LocalDate.of(2022, 4, 10)).
                academicTitle(AcademicTitle.builder().
                        academicTitleCode("#11").
                        academicTitleName("Graduate Assistant").
                        build()
                ).
                scientificField(ScientificField.builder().
                        scientificFieldCode("#01").
                        scientificFieldName("Algorithms and Data Structures").
                        build()).
                member(member).
                build();

        when(academicTitleHistoryConverter.toDTO(academicTitleHistory))
                .thenReturn(dto);

        when(academicTitleHistoryConverter.toDTO(academicTitleHistory2))
                .thenReturn(dto2);
    }

    @Test
    void getAllTest() {
        List<AcademicTitleHistory> list = Arrays.asList(academicTitleHistory, academicTitleHistory2);
        List<AcademicTitleHistoryDTO> listDTO = Arrays.asList(dto, dto2);

        when(academicTitleHistoryRepository.findAll()).thenReturn(list);
        when(academicTitleHistoryConverter.entitiesToDTOs(list))
                .thenReturn(listDTO);

        val result = academicTitleHistoryService.getAll();
        assertEquals(listDTO, result);
    }

    @Test
    void findAllByMemberIdSuccessTest() throws Exception {
        List<AcademicTitleHistory> list = Arrays.asList(academicTitleHistory, academicTitleHistory2);
        List<AcademicTitleHistoryDTO> listDTO = Arrays.asList(dto, dto2);

        when(memberRepository.findById(member.getId()))
                .thenReturn(Optional.of(member));
        when(academicTitleHistoryRepository.findAllByMemberId(member.getId()))
                .thenReturn(list);
        when(academicTitleHistoryConverter.entitiesToDTOs(list))
                .thenReturn(listDTO);

        val result = academicTitleHistoryService.findAllByMemberId(member.getId());
        assertEquals(listDTO, result);
    }

    @Test
    void saveSuccessTest() throws Exception {
        when(academicTitleRepository.findByAcademicTitleName(dto2.getAcademicTitle()))
                .thenReturn(Optional.of(at));
        when(scientificFieldRepository.findByScientificFieldName(dto2.getScientificField()))
                .thenReturn(Optional.of(sf));
        when(memberRepository.findById(dto2.getMemberId()))
                .thenReturn(Optional.of(member));
        when(academicTitleHistoryRepository.save(academicTitleHistory2))
                .thenReturn(academicTitleHistory2);

        val result = academicTitleHistoryService.save(dto2);

        assertNotNull(result);
        assertEquals(dto2, result);
    }

    @Test
    void saveFailureTest() throws Exception {
        val badDateDTO = AcademicTitleHistoryDTO.builder().
                startDate(LocalDate.now()).
                endDate(LocalDate.now().minusDays(1)).
                build();
        Exception ex = assertThrows(Exception.class, () -> {
            academicTitleHistoryService.save(badDateDTO);
        });
        assertEquals("End end must be after start date!", ex.getMessage());
    }

    @Test
    void updateAcademicTitleHistorySuccessTest() throws Exception {
        val at2 = new AcademicTitle("#07", "Dean");
        val sf2 = new ScientificField("#05", "Software Testing and Quality Assurance");

        when(academicTitleHistoryRepository.findById(dto.getId()))
                .thenReturn(Optional.of(academicTitleHistory));
        when(academicTitleRepository.findByAcademicTitleName(at2.getAcademicTitleName()))
                .thenReturn(Optional.of(at2));
        when(scientificFieldRepository.findByScientificFieldName(sf2.getScientificFieldName()))
                .thenReturn(Optional.of(sf2));
        when(memberRepository.findById(member.getId()))
                .thenReturn(Optional.of(member));

        academicTitleHistory.setAcademicTitle(at2);
        academicTitleHistory.setScientificField(sf2);

        dto.setAcademicTitle(at2.getAcademicTitleName());
        dto.setScientificField(sf2.getScientificFieldName());

        when(academicTitleHistoryRepository.save(academicTitleHistory))
                .thenReturn(academicTitleHistory);

        val result = academicTitleHistoryService.updateAcademicTitleHistory(academicTitleHistory.getId(), dto);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void updateAcademicTitleHistoryFailureTest() {
        when(academicTitleHistoryRepository.findById(academicTitleHistory.getId())).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> {
            academicTitleHistoryService.updateAcademicTitleHistory(academicTitleHistory.getId(), dto);
        });
        assertEquals("Academic Title History with the given ID doesn't exist in the database!", ex.getMessage());
    }

    @Test
    void deleteSuccessTest() throws Exception {
        when(academicTitleHistoryRepository.findById(academicTitleHistory.getId())).thenReturn(Optional.of(academicTitleHistory));
        academicTitleHistoryService.delete(academicTitleHistory.getId());
        verify(academicTitleHistoryRepository, times(1)).deleteById(academicTitleHistory.getId());
    }

    @Test
    void deleteFailureTest() {
        when(academicTitleHistoryRepository.findById(academicTitleHistory.getId())).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> {
            academicTitleHistoryService.delete(academicTitleHistory.getId());
        });
        assertEquals("Academic Title History with the given ID does not exist!", ex.getMessage());
    }
}