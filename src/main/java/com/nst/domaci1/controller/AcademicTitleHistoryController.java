package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.AcademicTitleHistoryConverter;
import com.nst.domaci1.domain.AcademicTitleHistory;
import com.nst.domaci1.domain.MemberID;
import com.nst.domaci1.dto.AcademicTitleHistoryDTO;
import com.nst.domaci1.service.AcademicTitleHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.NotSupportedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academicTitleHistory")
public class AcademicTitleHistoryController {

    private AcademicTitleHistoryService academicTitleHistoryService;
    private AcademicTitleHistoryConverter academicTitleHistoryConverter;

    public AcademicTitleHistoryController(AcademicTitleHistoryService academicTitleHistoryService, AcademicTitleHistoryConverter academicTitleHistoryConverter) {
        this.academicTitleHistoryService = academicTitleHistoryService;
        this.academicTitleHistoryConverter = academicTitleHistoryConverter;
    }


    @Operation(summary = "GET ALL Academic Title History")
    @GetMapping
    public ResponseEntity<List<AcademicTitleHistoryDTO>> getAll() {
        List<AcademicTitleHistoryDTO> list = academicTitleHistoryConverter.entitiesToDTOs(academicTitleHistoryService.getAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Academic Title History - PAGEABLE")
    @GetMapping("/paging")
    public ResponseEntity<Page<AcademicTitleHistoryDTO>>getAllByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "academicTitle") String academicTitle,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection){

        Pageable pageable;
        if (sortDirection.equals("asc")){
            pageable = PageRequest.of(page, pageSize, Sort.by(academicTitle).ascending());
        }else {
            pageable = PageRequest.of(page, pageSize, Sort.by(academicTitle).descending());
        }
        Page<AcademicTitleHistoryDTO> academicTitleHistoriesDTOPage = academicTitleHistoryService.getALl(pageable).map(academicTitleHistoryConverter::toDTO);
        return new ResponseEntity<>(academicTitleHistoriesDTOPage, HttpStatus.OK);

    }


    @Operation(summary = "GET ALL Academic Title Histories by Member (firstname and lastname)")
    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<?>findAllByMember(@PathVariable String firstName, @PathVariable String lastName) {
        List<AcademicTitleHistoryDTO> titlesOfMember = null;
        try {
            titlesOfMember = academicTitleHistoryConverter.entitiesToDTOs(academicTitleHistoryService.findAllByMember(firstName, lastName));
            return new ResponseEntity<>(titlesOfMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
