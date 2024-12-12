package com.nst.domaci1.controller;

import com.nst.domaci1.dto.*;
import com.nst.domaci1.service.AcademicTitleHistoryService;
import com.nst.domaci1.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final AcademicTitleHistoryService academicTitleHistoryService;


    @Operation(summary = "SAVE new Member")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MemberDTO dto) {
        try {
            MemberDTO memDTO = memberService.save(dto);
            return new ResponseEntity<>(memDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "GET ALL Members")
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAll() {
        List<MemberDTO> membersDTO = memberService.getAll();
        return new ResponseEntity<>(membersDTO, HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Members - PAGEABLE")
    @GetMapping("/paging")
    public ResponseEntity<Page<MemberDTO>> getAllByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "lastName") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {

        Pageable pageable;
        if (sortDirection.equals("asc")) {
            pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        }

        Page<MemberDTO> membersDTOPage = memberService.getAll(pageable);

        return new ResponseEntity<>(membersDTOPage, HttpStatus.OK);
    }


    @Operation(summary = "DELETE Member by it's ID")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> delete(@PathVariable Long memberId) {
        try {
            memberService.delete(memberId);
            return new ResponseEntity<>("Member successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "UPDATE Member's fields regarding science")
    @PatchMapping("/updateScienceFields/{memberId}")
    public ResponseEntity<?> updateScienceFields(@PathVariable Long memberId, @Valid @RequestBody MemberChangeAllScienceFieldsDTO dto) throws Exception {

        try {
            val memberDTO = memberService.updateScienceFields(memberId, dto);
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @Operation(summary = "UPDATE Member's department to which belongs")
    @PatchMapping("/updateDepartment/{memberId}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long memberId, @Valid @RequestBody MemberChangeDepartmentDTO changeDepartmentDTO) throws Exception {
        try {
            val memberDTO = memberService.updateDepartment(memberId, changeDepartmentDTO);
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "GET Member by it's ID")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> findById(@PathVariable Long memberId) {
        try {
            MemberDTO memDTO = memberService.findById(memberId);
            return new ResponseEntity<>(memDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////


    @Operation(summary = "GET ALL Academic Title Histories")
    @GetMapping("/academicTitleHistories")
    public ResponseEntity<List<AcademicTitleHistoryDTO>> getAllAcademicTitleHistories() {
        List<AcademicTitleHistoryDTO> list = academicTitleHistoryService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Academic Title Histories - PAGEABLE")
    @GetMapping("/academicTitleHistories/paging")
    public ResponseEntity<Page<AcademicTitleHistoryDTO>> getAllByPageAcademicTitleHistories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "academicTitle") String academicTitle,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {

        Pageable pageable;
        if (sortDirection.equals("asc")) {
            pageable = PageRequest.of(page, pageSize, Sort.by(academicTitle).ascending());
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(academicTitle).descending());
        }
        Page<AcademicTitleHistoryDTO> academicTitleHistoriesDTOPage = academicTitleHistoryService.getALl(pageable);
        return new ResponseEntity<>(academicTitleHistoriesDTOPage, HttpStatus.OK);

    }


    @Operation(summary = "GET ALL Academic Title Histories by Member ID")
    @GetMapping("/{memberId}/academicTitleHistories")
    public ResponseEntity<?> findAllAcademicTitleHistoriesByMember(@PathVariable Long memberId) {
        try {
            val titlesOfMember = academicTitleHistoryService.findAllByMemberId(memberId);
            return new ResponseEntity<>(titlesOfMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @Operation(summary = "SAVE new Academic Title History")
    @PostMapping("/academicTitleHistory")
    public ResponseEntity<?> saveAcademicTitleHistory(@Valid @RequestBody AcademicTitleHistoryDTO dto) {
        try {
            AcademicTitleHistoryDTO createdDTO = academicTitleHistoryService.save(dto);
            return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "UPDATE Academic Title History")
    @PutMapping("/academicTitleHistory/{academicTitleHistoryId}")
    public ResponseEntity<?> updateAcademicTitleHistory(@PathVariable Long academicTitleHistoryId, @Valid @RequestBody AcademicTitleHistoryDTO dto) {
        try {
            val updatedAcademicTitle = academicTitleHistoryService.updateAcademicTitleHistory(academicTitleHistoryId, dto);
            return new ResponseEntity<>(updatedAcademicTitle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "DELETE Academic Title History by it's ID")
    @DeleteMapping("/academicTitle/{academicTitleId}")
    public ResponseEntity<String> deleteAcademicTitleHistory(@PathVariable Long academicTitleId) {
        try {
            academicTitleHistoryService.delete(academicTitleId);
            return new ResponseEntity<>("Academic Title History successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
