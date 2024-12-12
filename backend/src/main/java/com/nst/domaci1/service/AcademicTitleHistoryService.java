package com.nst.domaci1.service;

import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AcademicTitleHistoryService {

    List<AcademicTitleHistoryDTO> getAll();

    Page<AcademicTitleHistoryDTO> getALl(Pageable pageable);

    List<AcademicTitleHistoryDTO> findAllByMemberId(Long id) throws Exception;

    AcademicTitleHistoryDTO save(AcademicTitleHistoryDTO dto) throws Exception;

    AcademicTitleHistoryDTO updateAcademicTitleHistory(Long academicTitleHistoryId, AcademicTitleHistoryDTO dto) throws Exception;

    void delete(Long academicTitleHistoryId) throws Exception;
}
