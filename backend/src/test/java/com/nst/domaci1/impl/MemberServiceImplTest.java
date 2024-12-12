package com.nst.domaci1.impl;


import com.nst.domaci1.converter.impl.MemberConverter;
import com.nst.domaci1.domain.*;
import com.nst.domaci1.dto.MemberChangeAllScienceFieldsDTO;
import com.nst.domaci1.dto.MemberChangeDepartmentDTO;
import com.nst.domaci1.dto.MemberDTO;
import com.nst.domaci1.repository.*;
import com.nst.domaci1.service.MemberService;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private AcademicTitleRepository academicTitleRepository;

    @MockBean
    private EducationTitleRepository educationTitleRepository;

    @MockBean
    private ScientificFieldRepository scientificFieldRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private AcademicTitleHistoryRepository academicTitleHistoryRepository;

    @MockBean
    private MemberConverter memberConverter;


    private final AcademicTitle academicTitle = AcademicTitle.builder().
            academicTitleCode("#01").
            academicTitleName("Professor").
            build();

    private final EducationTitle educationTitle = EducationTitle.builder().
            educationTitleCode("#01").
            educationTitleName("Doctoral Degree").
            build();

    private final ScientificField scientificField = ScientificField.builder()
            .scientificFieldCode("#01")
            .scientificFieldName("Artificial Intelligence")
            .build();

    private final Department department = Department.builder()
            .name("Katedra za softversko inzenjerstvo")
            .build();


    private final Member member = new Member(
            null,
            "Zika",
            "Zikic",
            academicTitle,
            educationTitle,
            scientificField,
            department
    );

    private final MemberDTO memberDTO = new MemberDTO(
            1L,
            "Zika",
            "Zikic",
            academicTitle.getAcademicTitleName(),
            educationTitle.getEducationTitleName(),
            scientificField.getScientificFieldName(),
            department.getName()
    );

    private Member member2;
    AcademicTitle at2 = AcademicTitle.builder().
            academicTitleCode("#02").
            academicTitleName("Adjunct Professor").
            build();

    EducationTitle et2 = EducationTitle.builder().
            educationTitleCode("#02").
            educationTitleName("Master Degree").
            build();

    ScientificField sf2 = ScientificField.builder().
            scientificFieldCode("#02").
            scientificFieldName("Database Administration").
            build();

    private MemberDTO memberDTO2;

    @BeforeEach
    void setUp() {

        member2 = new Member(
                2L,
                "Pera",
                "Peric",
                academicTitle,
                educationTitle,
                scientificField,
                department
        );

        memberDTO2 = new MemberDTO(
                2L,
                "Pera",
                "Peric",
                at2.getAcademicTitleName(),
                et2.getEducationTitleName(),
                sf2.getScientificFieldName(),
                department.getName()
        );

        when(memberConverter.toDTO(member))
                .thenReturn(memberDTO);

        when(memberConverter.toDTO(member2))
                .thenReturn(memberDTO2);
    }

    @Test
    void saveMemberSuccessTest() throws Exception {

        when(academicTitleRepository
                .findByAcademicTitleName(memberDTO.getAcademicTitle()))
                .thenReturn(Optional.of(academicTitle));
        when(educationTitleRepository
                .findByEducationTitleName(memberDTO.getEducationTitle()))
                .thenReturn(Optional.of(educationTitle));
        when(scientificFieldRepository
                .findByScientificFieldName(memberDTO.getScientificField()))
                .thenReturn(Optional.of(scientificField));
        when(departmentRepository
                .findById(eq(memberDTO.getDepartmentName())))
                .thenReturn(Optional.of(department));

        when(memberRepository.save(member)).thenReturn(member);

        MemberDTO result = memberService.save(memberDTO);

        assertNotNull(result);
        assertEquals(memberDTO, result);
    }

    @Test
    void getAllTest() {

        List<Member> members = Arrays.asList(member, member2);
        List<MemberDTO> membersDTO = Arrays.asList(memberDTO, memberDTO2);

        when(memberRepository.findAll()).thenReturn(members);

        when(memberConverter.entitiesToDTOs(members))
                .thenReturn(membersDTO);

        List<MemberDTO> result = memberService.getAll();

        assertEquals(membersDTO, result);
    }

    @Test
    void deleteSuccessTest() throws Exception {
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        memberService.delete(member.getId());
        verify(memberRepository, times(1)).deleteById(member.getId());
    }

    @Test
    void deleteFailureTest() {
        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {
            memberService.delete(member.getId());
        });
    }

    @Test
    void updateScienceFieldsSuccessTest() throws Exception {
        MemberChangeAllScienceFieldsDTO dto = MemberChangeAllScienceFieldsDTO.builder().
                academicTitle(at2.getAcademicTitleName()).
                educationTitle(et2.getEducationTitleName()).
                scienceField(sf2.getScientificFieldName()).
                build();


        when(memberRepository.findById(member2.getId()))
                .thenReturn(Optional.of(member2));

        when(academicTitleRepository.findByAcademicTitleName(at2.getAcademicTitleName()))
                .thenReturn(Optional.of(at2));
        when(educationTitleRepository.findByEducationTitleName(et2.getEducationTitleName()))
                .thenReturn(Optional.of(et2));
        when(scientificFieldRepository.findByScientificFieldName(sf2.getScientificFieldName()))
                .thenReturn(Optional.of(sf2));

        member2.setAcademicTitle(at2);
        member2.setEducationTitle(et2);
        member2.setScientificField(sf2);

        when(memberRepository.save(member2)).
                thenReturn(member2);

        val updatedMemberDTO = memberService.updateScienceFields(member2.getId(), dto);

        assertNotNull(updatedMemberDTO);
        assertEquals(memberDTO2, updatedMemberDTO);
    }

    @Test
    void updateDepartmentSuccessTest() throws Exception {
        Department d = Department.builder().
                name("Katedra za informacione sisteme").
                shortName("IS").
                supervisorId(3L).
                secretaryId(4L).
                build();

        MemberChangeDepartmentDTO dtoChange = new MemberChangeDepartmentDTO(d.getName());

        when(memberRepository.findById(member2.getId()))
                .thenReturn(Optional.of(member2));
        when(departmentRepository.findById(d.getName()))
                .thenReturn(Optional.of(d));

        member2.setDepartment(d);
        memberDTO2.setDepartmentName(d.getName());

        when(memberRepository.save(member2))
                .thenReturn(member2);
        when(memberConverter.toDTO(member2))
                .thenReturn(memberDTO2);

        val result = memberService.updateDepartment(member2.getId(), dtoChange);

        assertNotNull(result);
        assertEquals(memberDTO2, result);
    }

    @Test
    void findByIdSuccessTest() throws Exception {
        when(memberRepository.findById(memberDTO2.getId())).thenReturn(Optional.of(member2));
        MemberDTO foundMember = memberService.findById(memberDTO2.getId());
        assertNotNull(foundMember);
        assertEquals(memberDTO2, foundMember);
    }

    @Test
    void findByIdFailureTest() throws Exception {
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> {
            memberService.findById(member2.getId());
        });
    }
}