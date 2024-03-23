package com.nst.domaci1.controller;

import com.nst.domaci1.dto.EducationTitleDTO;
import com.nst.domaci1.service.EducationTitleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/educationTitle")
public class EducationTitleController {

    private final EducationTitleService educationTitleService;

    @Operation(summary = "SAVE new Education Title")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EducationTitleDTO dto) {
        try {
            EducationTitleDTO etDTO = educationTitleService.save(dto);
            return new ResponseEntity<>(etDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @Operation(summary = "GET ALL Education Titles")
    @GetMapping
    public ResponseEntity<List<EducationTitleDTO>> getAll() {
        List<EducationTitleDTO> list = educationTitleService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "DELETE Education Title by it's ID - code")
    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable String code) {
        try {
            educationTitleService.delete(code);
            return new ResponseEntity<>("Education Title successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "GET Education Title by it's name")
    @GetMapping("/{educationTitle}")
    public ResponseEntity<?> findByName(@PathVariable String educationTitle) {
        try {
            EducationTitleDTO etDTO = educationTitleService.findByName(educationTitle);
            return new ResponseEntity<>(etDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
