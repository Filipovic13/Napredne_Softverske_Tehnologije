package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.MemberConverter;
import com.nst.domaci1.domain.*;
import com.nst.domaci1.dto.MemberChangeAllScienceFieldsDTO;
import com.nst.domaci1.dto.MemberChangeDepartmentDTO;
import com.nst.domaci1.dto.MemberDTO;
import com.nst.domaci1.repository.*;
import com.nst.domaci1.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final AcademicTitleHistoryRepository academicTitleHistoryRepository;
    private final AcademicTitleRepository academicTitleRepository;
    private final EducationTitleRepository educationTitleRepository;
    private final ScientificFieldRepository scientificFieldRepository;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public MemberDTO save(MemberDTO dto) throws Exception {

        Optional<AcademicTitle> academicTitleDB = academicTitleRepository
                .findByAcademicTitleName(dto.getAcademicTitle());

        if (academicTitleDB.isEmpty()) {
            throw new Exception("Academic Title doesn't exist!\n" +
                    " Enter one of the values that exist in database: \n " +
                    academicTitleRepository.findAllAcademicTitleNames());
        }

        Optional<EducationTitle> educationTitleDB = educationTitleRepository
                .findByEducationTitleName(dto.getEducationTitle());

        if (educationTitleDB.isEmpty()) {
            throw new Exception("Education Title doesn't exist!\n" +
                    " Enter one of the values that exist in database: \n " +
                    educationTitleRepository.findAllEducationTitlesNames());
        }

        Optional<ScientificField> scientificFieldDB = scientificFieldRepository
                .findByScientificFieldName(dto.getScientificField());

        if (scientificFieldDB.isEmpty()) {
            throw new Exception("Scientific Field doesn't exist!\n " +
                    "Enter one of the values that exist in database: \n " +
                    scientificFieldRepository.findAllScientificFieldNames());
        }

        Optional<Department> depDB = departmentRepository.findById(dto.getDepartmentName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \n" +
                    "Enter one of next values: \n"
                    + departmentRepository.findAllNames());
        }

        final Member member = new Member(
                null,
                dto.getFirstName(),
                dto.getLastName(),
                academicTitleDB.get(),
                educationTitleDB.get(),
                scientificFieldDB.get(),
                depDB.get().getName()
        );

        final Member savedMember = memberRepository.save(member);

        academicTitleHistoryRepository.save(new AcademicTitleHistory(null, LocalDate.now(), null,
                academicTitleDB.get(), scientificFieldDB.get(), savedMember));

        return memberConverter.toDTO(savedMember);
    }

    @Override
    public List<MemberDTO> getAll() {
        return memberConverter.entitiesToDTOs(memberRepository.findAll());
    }

    @Override
    public Page<MemberDTO> getAll(Pageable pageable) {
        return memberRepository.findAll(pageable).map(memberConverter::toDTO);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        Optional<Member> memDB = memberRepository.findById(id);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        List<AcademicTitleHistory> academicTitleHistories = academicTitleHistoryRepository
                .findAllByMemberId(id);

        for (AcademicTitleHistory history : academicTitleHistories) {
            academicTitleHistoryRepository.delete(history);
        }
        memberRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MemberDTO updateScienceFields(Long memberId, MemberChangeAllScienceFieldsDTO changeScienceFieldsDTO) throws Exception {

        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        Optional<AcademicTitle> academicTitleDB = academicTitleRepository.findByAcademicTitleName(changeScienceFieldsDTO.getAcademicTitle());
        if (academicTitleDB.isEmpty()) {
            throw new Exception("Academic Title doesn't exist!\n Enter one of the values that exist in database: \n " + academicTitleRepository.findAllAcademicTitleNames());
        }

        Optional<EducationTitle> educationTitleDB = educationTitleRepository.findByEducationTitleName(changeScienceFieldsDTO.getEducationTitle());
        if (educationTitleDB.isEmpty()) {
            throw new Exception("Education Title doesn't exist!\n Enter one of the values that exist in database: \n " + educationTitleRepository.findAllEducationTitlesNames());
        }

        Optional<ScientificField> scientificFieldDB = scientificFieldRepository.findByScientificFieldName(changeScienceFieldsDTO.getScienceField());
        if (scientificFieldDB.isEmpty()) {
            throw new Exception("Scientific Field doesn't exist!\n Enter one of the values that exist in database: \n " + scientificFieldRepository.findAllScientificFieldNames());
        }


        // new academic title can't be the same as the previous - from Academic Title History
        Optional<AcademicTitleHistory> lastTitleHistoryRecord = academicTitleHistoryRepository.findFirstByMemberIdOrderByStartDateDesc(memberId);
        if (lastTitleHistoryRecord.isPresent() && lastTitleHistoryRecord.get().getAcademicTitle().getAcademicTitleName().equals(changeScienceFieldsDTO.getAcademicTitle())) {
            throw new Exception("Entered Academic Title name is already last title.\nPlease enter different Academic title name if you want to make changes!");
        }

        // if last record exists set end date
        if (lastTitleHistoryRecord.isPresent()) {
            lastTitleHistoryRecord.get().setEndDate(LocalDate.now());
            academicTitleHistoryRepository.save(lastTitleHistoryRecord.get());
        }

        LocalDate startDate;
        if (lastTitleHistoryRecord.isPresent() && lastTitleHistoryRecord.get().getStartDate().equals(LocalDate.now())) {
            startDate = lastTitleHistoryRecord.get().getEndDate().plusDays(1);
        } else {
            startDate = LocalDate.now();
        }


        Member member = memDB.get();
        member.setAcademicTitle(academicTitleDB.get());
        member.setEducationTitle(educationTitleDB.get());
        member.setScientificField(scientificFieldDB.get());

        Member savedMember = memberRepository.save(member);

        academicTitleHistoryRepository.save(new AcademicTitleHistory(null, startDate, null, academicTitleDB.get(), scientificFieldDB.get(), savedMember));

        return memberConverter.toDTO(savedMember);
    }

    @Override
    @Transactional
    public MemberDTO updateDepartment(Long memberId, MemberChangeDepartmentDTO changeDepartmentDTO) throws Exception {

        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        Optional<Department> depDB = departmentRepository.findById(changeDepartmentDTO.getDepartmentName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }

        Member member = memDB.get();
        member.setDepartment(depDB.get().getName());

        val savedMember = memberRepository.save(member);

        return memberConverter.toDTO(savedMember);
    }

    @Override
    public MemberDTO findById(Long id) throws Exception {
        Optional<Member> memDB = memberRepository.findById(id);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }
        return memberConverter.toDTO(memDB.get());
    }


}
