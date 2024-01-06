package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.*;
import com.nst.domaci1.repository.*;
import com.nst.domaci1.service.AcademicTitleHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicTitleHistoryServiceImpl implements AcademicTitleHistoryService {

    private AcademicTitleHistoryRepository academicTitleHistoryRepository;
    private MemberRepository memberRepository;
    private AcademicTitleRepository academicTitleRepository;
    private EducationTitleRepository educationTitleRepository;
    private ScientificFieldRepository scientificFieldRepository;

    public AcademicTitleHistoryServiceImpl(AcademicTitleHistoryRepository academicTitleHistoryRepository, MemberRepository memberRepository, AcademicTitleRepository academicTitleRepository, EducationTitleRepository educationTitleRepository, ScientificFieldRepository scientificFieldRepository) {
        this.academicTitleHistoryRepository = academicTitleHistoryRepository;
        this.memberRepository = memberRepository;
        this.academicTitleRepository = academicTitleRepository;
        this.educationTitleRepository = educationTitleRepository;
        this.scientificFieldRepository = scientificFieldRepository;
    }

    @Override
    public List<AcademicTitleHistory> getAll() {
        return academicTitleHistoryRepository.findAll();
    }


    @Override
    public Page<AcademicTitleHistory> getALl(Pageable pageable) {
        return academicTitleHistoryRepository.findAll(pageable);
    }

    @Override
    public List<AcademicTitleHistory> findAllByMember(String firstName, String lastName) throws Exception {
        MemberID memberID = new MemberID(firstName, lastName);

        Optional<Member> memberDB = memberRepository.findById(memberID);
        if (memberDB.isEmpty()){
            throw new Exception("Member with the given firstname and lastname doesn't exist in database!");
        }

        return academicTitleHistoryRepository.findAllByMemberFirstNameAndMemberLastName(firstName, lastName);
    }
}
