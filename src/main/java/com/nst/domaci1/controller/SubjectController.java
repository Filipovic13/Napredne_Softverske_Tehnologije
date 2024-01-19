package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.SubjectConverter;
import com.nst.domaci1.domain.Subject;
import com.nst.domaci1.dto.SubjectDTO;
import com.nst.domaci1.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.NotSupportedException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private SubjectService subjectService;
    private SubjectConverter subjectConverter;

    public SubjectController(SubjectService subjectService, SubjectConverter subjectConverter) {
        this.subjectService = subjectService;
        this.subjectConverter = subjectConverter;
    }

    @Operation(summary = "SAVE new Subject")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody SubjectDTO subjectDTO) {
        Subject s = subjectConverter.toEntity(subjectDTO);
        try {
            SubjectDTO subjDTO = subjectConverter.toDTO(subjectService.save(s));
            return new ResponseEntity<>(subjDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "GET ALL Subjects")
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAll() {
        List<SubjectDTO> subjectsDTO = subjectConverter.entitiesToDTOs(subjectService.getAll());
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

    @Operation(summary = "UPDATE ESPB value for Subject")
    @PatchMapping("/{id}/{espb}")
    public ResponseEntity<?> update(@PathVariable Long id, @Min(value = 2, message = "espb must be at least 2") @Max(value = 6, message = "espb must be at most 6") @PathVariable int espb) {
        try {
            SubjectDTO subjDTO = subjectConverter.toDTO(subjectService.update(id, espb));
            return new ResponseEntity<>(subjDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.OK);
        }

    }

    @Operation(summary = "GET Subject by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            SubjectDTO subjDTO = subjectConverter.toDTO(subjectService.findById(id));
            return new ResponseEntity<>(subjDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
