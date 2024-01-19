package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.AcademicTitleHistoryConverter;
import com.nst.domaci1.converter.impl.MemberConverter;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import com.nst.domaci1.dto.MemberDTO;
import com.nst.domaci1.service.AcademicTitleHistoryService;
import com.nst.domaci1.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;
    private MemberConverter memberConverter;
    private AcademicTitleHistoryService academicTitleHistoryService;
    private AcademicTitleHistoryConverter academicTitleHistoryConverter;

    public MemberController(MemberService memberService, MemberConverter memberConverter, AcademicTitleHistoryService academicTitleHistoryService, AcademicTitleHistoryConverter academicTitleHistoryConverter) {
        this.memberService = memberService;
        this.memberConverter = memberConverter;
        this.academicTitleHistoryService = academicTitleHistoryService;
        this.academicTitleHistoryConverter = academicTitleHistoryConverter;
    }

    @Operation(summary = "SAVE new Member")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MemberDTO memberDTO) {
        Member member = memberConverter.toEntity(memberDTO);
        try {
            MemberDTO memDTO = memberConverter.toDTO(memberService.save(member));
            return new ResponseEntity<>(memDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "GET ALL Members")
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAll() {
        List<MemberDTO> membersDTO = memberConverter.entitiesToDTOs(memberService.getAll());
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

        Page<MemberDTO> membersDTOPage = memberService.getAll(pageable).map(memberConverter::toDTO);

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
    @PatchMapping("/{memberId}/{academicTitle}/{educationTitle}/{scienceField}")
    public ResponseEntity<?> updateScienceFields(@PathVariable Long memberId, @PathVariable String academicTitle, @PathVariable String educationTitle, String scienceField) throws Exception {
        try {
            Member member = memberService.updateScienceFields(memberId, academicTitle, educationTitle, scienceField);
            return new ResponseEntity<>(memberConverter.toDTO(member), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "UPDATE Member's department to which belongs")
    @PatchMapping("/{memberId}/{departmentName}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long memberId, @PathVariable String departmentName) throws Exception {
        try {
            Member member = memberService.updateDepartment(memberId, departmentName);
            return new ResponseEntity<>(memberConverter.toDTO(member), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "GET Member by it's ID")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> findById(@PathVariable Long memberId) {
        try {
            MemberDTO memDTO = memberConverter.toDTO(memberService.findById(memberId));
            return new ResponseEntity<>(memDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////


    @Operation(summary = "GET ALL Academic Title History")
    @GetMapping("/academicTitles")
    public ResponseEntity<List<AcademicTitleHistoryDTO>> getAllAcademicTitleHistories() {
        List<AcademicTitleHistoryDTO> list = academicTitleHistoryConverter.entitiesToDTOs(academicTitleHistoryService.getAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Academic Title History - PAGEABLE")
    @GetMapping("/academicTitles/paging")
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
        Page<AcademicTitleHistoryDTO> academicTitleHistoriesDTOPage = academicTitleHistoryService.getALl(pageable).map(academicTitleHistoryConverter::toDTO);
        return new ResponseEntity<>(academicTitleHistoriesDTOPage, HttpStatus.OK);

    }


    @Operation(summary = "GET ALL Academic Title Histories by Member ID")
    @GetMapping("/academicTitles/{memberId}")
    public ResponseEntity<?> findAllAcademicTitleHistoriesByMember(@PathVariable Long memberId) {
        List<AcademicTitleHistoryDTO> titlesOfMember = null;
        try {
            titlesOfMember = academicTitleHistoryConverter.entitiesToDTOs(academicTitleHistoryService.findAllByMemberId(memberId));
            return new ResponseEntity<>(titlesOfMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
