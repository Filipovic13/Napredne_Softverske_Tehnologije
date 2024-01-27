package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.*;
import com.nst.domaci1.dto.AcademicTitleHistorySaveUpdateDTO;
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
    public List<AcademicTitleHistory> findAllByMemberId(Long memberId) throws Exception {

        Optional<Member> memberDB = memberRepository.findById(memberId);
        if (memberDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist in database!");
        }

        return academicTitleHistoryRepository.findAllByMemberId(memberId);
    }

    @Override
    public AcademicTitleHistory save(AcademicTitleHistorySaveUpdateDTO dto) throws Exception {

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new Exception("End end must be after start date!");
        }

        Optional<AcademicTitle> academicTitleDB = academicTitleRepository.findByAcademicTitleName(dto.getAcademicTitle());
        if (academicTitleDB.isEmpty()) {
            throw new Exception("Academic Title doesn't exist!\n Enter one of the values that exist in database: \n " + academicTitleRepository.findAllAcademicTitleNames());
        }

        Optional<ScientificField> scientificFieldDB = scientificFieldRepository.findByScientificFieldName(dto.getScientificField());
        if (scientificFieldDB.isEmpty()) {
            throw new Exception("Scientific Field doesn't exist!\n Enter one of the values that exist in database: \n " + scientificFieldRepository.findAllScientificFieldNames());
        }

        Optional<Member> memberDB = memberRepository.findById(dto.getMemberId());
        if (memberDB.isEmpty()) {
            throw new Exception("Member with the given ID does not exist!");
        }

        AcademicTitleHistory academicTitleHistory = new AcademicTitleHistory();
        academicTitleHistory.setStartDate(dto.getStartDate());
        academicTitleHistory.setEndDate(dto.getEndDate());
        academicTitleHistory.setAcademicTitle(academicTitleDB.get());
        academicTitleHistory.setScientificField(scientificFieldDB.get());
        academicTitleHistory.setMember(memberDB.get());

        return academicTitleHistoryRepository.save(academicTitleHistory);
    }

    @Override
    public AcademicTitleHistory updateAcademicTitleHistory(Long academicTitleHistoryId, AcademicTitleHistorySaveUpdateDTO dto) throws Exception {

        Optional<AcademicTitleHistory> academicTitleHistoryDB = academicTitleHistoryRepository.findById(academicTitleHistoryId);
        if (academicTitleHistoryDB.isEmpty()) {
            throw new Exception("Academic Title History with the given ID doesn't exist in the database!");
        }

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new Exception("End end must be after start date!");
        }

        Optional<AcademicTitle> academicTitleDB = academicTitleRepository.findByAcademicTitleName(dto.getAcademicTitle());
        if (academicTitleDB.isEmpty()) {
            throw new Exception("Academic Title doesn't exist!\n Enter one of the values that exist in the database: \n " + academicTitleRepository.findAllAcademicTitleNames());
        }

        Optional<ScientificField> scientificFieldDB = scientificFieldRepository.findByScientificFieldName(dto.getScientificField());
        if (scientificFieldDB.isEmpty()) {
            throw new Exception("Scientific Field doesn't exist!\n Enter one of the values that exist in the database: \n " + scientificFieldRepository.findAllScientificFieldNames());
        }

        Optional<Member> memberDB = memberRepository.findById(dto.getMemberId());
        if (memberDB.isEmpty()) {
            throw new Exception("Member with the given ID does not exist!");
        }

        AcademicTitleHistory academicTitleHistory = academicTitleHistoryDB.get();

        academicTitleHistory.setStartDate(dto.getStartDate());
        academicTitleHistory.setEndDate(dto.getEndDate());
        academicTitleHistory.setAcademicTitle(academicTitleDB.get());
        academicTitleHistory.setScientificField(scientificFieldDB.get());
        academicTitleHistory.setMember(memberDB.get());

        return academicTitleHistoryRepository.save(academicTitleHistory);
    }

    @Override
    public void delete(Long academicTitleHistoryId) throws Exception {
        Optional<AcademicTitleHistory> athDB = academicTitleHistoryRepository.findById(academicTitleHistoryId);
        if (athDB.isEmpty()) {
            throw new Exception("Academic Title History with the given ID does not exist!");
        }
        academicTitleHistoryRepository.deleteById(academicTitleHistoryId);
    }
}
