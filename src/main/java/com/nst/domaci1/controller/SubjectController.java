package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.SubjectConverter;
import com.nst.domaci1.dto.SubjectChangeEspbDTO;
import com.nst.domaci1.dto.SubjectDTO;
import com.nst.domaci1.dto.SubjectSaveUpdateDTO;
import com.nst.domaci1.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectConverter subjectConverter;


    @Operation(summary = "SAVE new Subject")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody SubjectDTO dto) {
        try {
            SubjectDTO subjDTO = subjectService.save(dto);
            return new ResponseEntity<>(subjDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "GET ALL Subjects")
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAll() {
        List<SubjectDTO> subjectsDTO = subjectService.getAll();
        return new ResponseEntity<>(subjectsDTO, HttpStatus.OK);
    }

    @Operation(summary = "DELETE Subject by it's ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            subjectService.delete(id);
            return new ResponseEntity<>("Subject successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //PATCH
    @Operation(summary = "UPDATE ESPB value for Subject")
    @PatchMapping("/updateEspb/{subjectId}")
    public ResponseEntity<?> update(@PathVariable long subjectId, @Valid @RequestBody SubjectChangeEspbDTO dto) {
        try {
            SubjectDTO chnageEspbDTO = subjectService.updateESPB(subjectId, dto);
            return new ResponseEntity<>(chnageEspbDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "GET Subject by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            SubjectDTO subjDTO = subjectService.findById(id);
            return new ResponseEntity<>(subjDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
