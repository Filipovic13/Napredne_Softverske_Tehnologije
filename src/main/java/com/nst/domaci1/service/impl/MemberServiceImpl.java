package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.*;
import com.nst.domaci1.repository.*;
import com.nst.domaci1.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private DepartmentRepository departmentRepository;
    private AcademicTitleHistoryRepository academicTitleHistoryRepository;
    private AcademicTitleRepository academicTitleRepository;
    private EducationTitleRepository educationTitleRepository;
    private ScientificFieldRepository scientificFieldRepository;

    public MemberServiceImpl(MemberRepository memberRepository, DepartmentRepository departmentRepository, AcademicTitleHistoryRepository academicTitleHistoryRepository, AcademicTitleRepository academicTitleRepository, EducationTitleRepository educationTitleRepository, ScientificFieldRepository scientificFieldRepository) {
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.academicTitleHistoryRepository = academicTitleHistoryRepository;
        this.academicTitleRepository = academicTitleRepository;
        this.educationTitleRepository = educationTitleRepository;
        this.scientificFieldRepository = scientificFieldRepository;
    }

    @Override
    @Transactional
    public Member save(Member member) throws Exception {
        Optional<AcademicTitle> academicTitleDB = academicTitleRepository.findByAcademicTitleName(member.getAcademicTitle().getAcademicTitleName());
        Optional<EducationTitle> educationTitleDB = educationTitleRepository.findByEducationTitleName(member.getEducationTitle().getEducationTitleName());
        Optional<ScientificField> scientificFieldDB = scientificFieldRepository.findByScientificFieldName(member.getScientificField().getScientificFieldName());

        if (academicTitleDB.isEmpty()) {
            throw new Exception("Academic Title doesn't exist!\n Enter one of the values that exist in database: \n " + academicTitleRepository.findAllAcademicTitleNames());
        }
        if (educationTitleDB.isEmpty()) {
            throw new Exception("Education Title doesn't exist!\n Enter one of the values that exist in database: \n " + educationTitleRepository.findAllEducationTitlesNames());
        }
        if (scientificFieldDB.isEmpty()) {
            throw new Exception("Scientific Field doesn't exist!\n Enter one of the values that exist in database: \n " + scientificFieldRepository.findAllScientificFieldNames());
        }


        if (!member.getManagerRole().toString().equalsIgnoreCase("NONE")) {
            throw new Exception("When adding new member, manager role MUST BE set to NONE.");
        }

        Optional<Department> depDB = departmentRepository.findById(member.getDepartment().getName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }

        member.setAcademicTitle(academicTitleDB.get());
        member.setEducationTitle(educationTitleDB.get());
        member.setScientificField(scientificFieldDB.get());

        member.setDepartment(depDB.get());
        Member savedMember = memberRepository.save(member);

        academicTitleHistoryRepository.save(new AcademicTitleHistory(null, LocalDate.now(), null, academicTitleDB.get(), scientificFieldDB.get(), savedMember));

        return savedMember;
    }

    @Override
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @Override
    public Page<Member> getAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        Optional<Member> memDB = memberRepository.findById(id);
        if (memDB.isPresent()) {
            if (memDB.get().getManagerRole() == ManagerRole.SUPERVISOR || memDB.get().getManagerRole() == ManagerRole.SECRETARY) {
                throw new Exception("You can not delete Supervisor or Secretary!\nFirst change member's role, then delete a member.");
            }

            List<AcademicTitleHistory> academicTitleHistories = academicTitleHistoryRepository.findAllByMemberId(id);
            for (AcademicTitleHistory history : academicTitleHistories) {
                academicTitleHistoryRepository.delete(history);
            }
            memberRepository.deleteById(id);
        } else {
            throw new Exception("Member with the given ID doesn't exist!");
        }

    }

    @Override
    @Transactional
    public Member updateScienceFields(Long memberId, String academicTitle, String educationTitle, String scienceField) throws Exception {

        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isEmpty()) {
            throw new Exception("Member with entered firstname and lastname doesn't exist!");
        }

        Optional<AcademicTitle> academicTitleDB = academicTitleRepository.findByAcademicTitleName(academicTitle);
        Optional<EducationTitle> educationTitleDB = educationTitleRepository.findByEducationTitleName(educationTitle);
        Optional<ScientificField> scientificFieldDB = scientificFieldRepository.findByScientificFieldName(scienceField);

        if (academicTitleDB.isEmpty()) {
            throw new Exception("Academic Title doesn't exist!\n Enter one of the values that exist in database: \n " + academicTitleRepository.findAllAcademicTitleNames());
        }
        if (educationTitleDB.isEmpty()) {
            throw new Exception("Education Title doesn't exist!\n Enter one of the values that exist in database: \n " + educationTitleRepository.findAllEducationTitlesNames());
        }
        if (scientificFieldDB.isEmpty()) {
            throw new Exception("Scientific Field doesn't exist!\n Enter one of the values that exist in database: \n " + scientificFieldRepository.findAllScientificFieldNames());
        }

        // new academic title can't be the same as the previous - from Academic Title History
        Optional<AcademicTitleHistory> lastTitleHistoryRecord = academicTitleHistoryRepository.findFirstByMemberIdOrderByStartDateDesc(memberId);
        if (lastTitleHistoryRecord.isPresent() && lastTitleHistoryRecord.get().getAcademicTitle().getAcademicTitleName().equals(academicTitle)) {
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

        return savedMember;
    }

    @Override
    @Transactional
    public Member updateDepartment(Long memberId, String departmentName) throws Exception {

        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }

        Member member = memDB.get();
        member.setDepartment(depDB.get());

        return memberRepository.save(member);
    }

    @Override
    public Member findById(Long id) throws Exception {
        Optional<Member> memDB = memberRepository.findById(id);
        if (memDB.isPresent()) {
            return memDB.get();
        } else {
            throw new Exception("Member with the given ID - name and lastname doesn't exist!");
        }
    }


}
