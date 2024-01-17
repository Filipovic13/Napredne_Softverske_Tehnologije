package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.EducationTitleConverter;
import com.nst.domaci1.domain.EducationTitle;
import com.nst.domaci1.dto.EducationTitleDTO;
import com.nst.domaci1.service.EducationTitleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educationTitle")
public class EducationTitleController {

    private EducationTitleService educationTitleService;
    private EducationTitleConverter educationTitleConverter;

    public EducationTitleController(EducationTitleService educationTitleService, EducationTitleConverter educationTitleConverter) {
        this.educationTitleService = educationTitleService;
        this.educationTitleConverter = educationTitleConverter;
    }

    @Operation(summary = "SAVE new Education Title")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody EducationTitleDTO educationTitleDTO){
        EducationTitle et = educationTitleConverter.toEntity(educationTitleDTO);
        try {
            EducationTitleDTO etDTO = educationTitleConverter.toDTO(educationTitleService.save(et));
            return new ResponseEntity<>(etDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @Operation(summary = "GET ALL Education Titles")
    @GetMapping
    public ResponseEntity<List<EducationTitleDTO>> getAll(){
        List<EducationTitleDTO> list = educationTitleConverter.entitiesToDTOs(educationTitleService.getAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "DELETE Education Title by it's ID - code")
    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable String code){
        try {
            educationTitleService.delete(code);
            return new ResponseEntity<>("Education Title successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "GET Education Title by it's name")
    @GetMapping("/{educationTitle}")
    public ResponseEntity<?> finfByName(@PathVariable String educationTitle){
        try {
            EducationTitleDTO etDTO = educationTitleConverter.toDTO(educationTitleService.findByName(educationTitle));
            return new ResponseEntity<>(etDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}