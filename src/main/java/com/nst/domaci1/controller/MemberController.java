package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.MemberConverter;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.domain.MemberID;
import com.nst.domaci1.dto.MemberDTO;
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

    public MemberController(MemberService memberService, MemberConverter memberConverter) {
        this.memberService = memberService;
        this.memberConverter = memberConverter;
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


    @Operation(summary = "DELETE Member by it's firstname and lastname")
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> delete(@PathVariable String firstName, @PathVariable String lastName) {
        MemberID memberID = new  MemberID(firstName, lastName);
        try {
            memberService.delete(memberID);
            return new ResponseEntity<>("Member successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "UPDATE Member's fields regarding science")
    @PatchMapping("/{firstName}/{lastName}/{academicTitle}/{educationTitle}/{scienceField}")
    public ResponseEntity<?> updateScienceFields(@PathVariable String firstName, @PathVariable String lastName, @PathVariable String academicTitle, @PathVariable String educationTitle, String scienceField) throws Exception {
        try {
            Member member = memberService.updateScienceFields(firstName, lastName, academicTitle, educationTitle, scienceField);
            return new ResponseEntity<>(memberConverter.toDTO(member), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "UPDATE Member's department to which belongs")
    @PatchMapping("/{firstName}/{lastName}/{departmentName}")
    public ResponseEntity<?> updateDepartment(@PathVariable String firstName, @PathVariable String lastName, @PathVariable String departmentName) throws Exception {
        try {
            Member member = memberService.updateDepartment(firstName, lastName, departmentName);
            return new ResponseEntity<>(memberConverter.toDTO(member), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "GET Member by it's firstname and lastname")
    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<?> findById(@PathVariable String firstName, @PathVariable String lastName) {
        MemberID memberID = new MemberID(firstName, lastName);
        try {
            MemberDTO memDTO =  memberConverter.toDTO(memberService.findById(memberID));
            return new ResponseEntity<>(memDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
