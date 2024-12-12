package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.AcademicTitleHistoryConverter;
import com.nst.domaci1.domain.*;
import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import com.nst.domaci1.repository.*;
import com.nst.domaci1.service.AcademicTitleHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcademicTitleHistoryServiceImpl implements AcademicTitleHistoryService {

    private final AcademicTitleHistoryRepository academicTitleHistoryRepository;
    private final MemberRepository memberRepository;
    private final AcademicTitleRepository academicTitleRepository;
    private final EducationTitleRepository educationTitleRepository;
    private final ScientificFieldRepository scientificFieldRepository;
    private final AcademicTitleHistoryConverter academicTitleHistoryConverter;


    @Override
    public List<AcademicTitleHistoryDTO> getAll() {
        return academicTitleHistoryConverter
                .entitiesToDTOs(academicTitleHistoryRepository.findAll());
    }


    @Override
    public Page<AcademicTitleHistoryDTO> getALl(Pageable pageable) {
        return academicTitleHistoryRepository
                .findAll(pageable)
                .map(academicTitleHistoryConverter::toDTO);
    }

    @Override
    public List<AcademicTitleHistoryDTO> findAllByMemberId(Long memberId) throws Exception {

        Optional<Member> memberDB = memberRepository.findById(memberId);
        if (memberDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist in database!");
        }
        val allByMember = academicTitleHistoryRepository.findAllByMemberId(memberId);

        return academicTitleHistoryConverter.entitiesToDTOs(allByMember);
    }

    @Override
    public AcademicTitleHistoryDTO save(AcademicTitleHistoryDTO dto) throws Exception {

        if (dto.getEndDate() != null && dto.getEndDate().isBefore(dto.getStartDate())) {
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

        val savedAcademicTitleHistory = academicTitleHistoryRepository.save(academicTitleHistory);

        return academicTitleHistoryConverter.toDTO(savedAcademicTitleHistory);
    }

    @Override
    public AcademicTitleHistoryDTO updateAcademicTitleHistory(Long academicTitleHistoryId, AcademicTitleHistoryDTO dto) throws Exception {

        Optional<AcademicTitleHistory> academicTitleHistoryDB = academicTitleHistoryRepository.findById(academicTitleHistoryId);
        if (academicTitleHistoryDB.isEmpty()) {
            throw new Exception("Academic Title History with the given ID doesn't exist in the database!");
        }

        if (dto.getEndDate() != null && dto.getEndDate().isBefore(dto.getStartDate())) {
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

        val saved = academicTitleHistoryRepository.save(academicTitleHistory);
        return academicTitleHistoryConverter.toDTO(saved);
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
