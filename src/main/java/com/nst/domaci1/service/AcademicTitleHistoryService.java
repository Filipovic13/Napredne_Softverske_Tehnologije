package com.nst.domaci1.service;

import com.nst.domaci1.domain.AcademicTitleHistory;
import com.nst.domaci1.domain.MemberID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AcademicTitleHistoryService {

    List<AcademicTitleHistory> getAll();

    Page<AcademicTitleHistory> getALl(Pageable pageable);

    List<AcademicTitleHistory> findAllByMember(String firstName, String lastName) throws Exception;
}
