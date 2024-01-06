package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.AcademicTitleConverter;
import com.nst.domaci1.domain.AcademicTitle;
import com.nst.domaci1.dto.AcademicTitleDTO;
import com.nst.domaci1.service.AcademicTitleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academicTitle")
public class AcademicTitleController {

    private AcademicTitleService academicTitleService;
    private AcademicTitleConverter academicTitleConverter;

    public AcademicTitleController(AcademicTitleService academicTitleService, AcademicTitleConverter academicTitleConverter) {
        this.academicTitleService = academicTitleService;
        this.academicTitleConverter = academicTitleConverter;
    }

    @Operation(summary = "SAVE new Academic Title")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody AcademicTitleDTO academicTitleDTO){
        AcademicTitle at = academicTitleConverter.toEntity(academicTitleDTO);
        try {
            AcademicTitleDTO atDTO = academicTitleConverter.toDTO(academicTitleService.save(at));
            return new ResponseEntity<>(atDTO, HttpStatus.CREATED) ;
        } catch (Exception e) {
           return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @Operation(summary = "GET ALL Academic Titles")
    @GetMapping
    public ResponseEntity<List<AcademicTitleDTO>> getAll(){
        List<AcademicTitleDTO> list = academicTitleConverter.entitiesToDTOs(academicTitleService.getAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "DELETE Academic Title by it's ID - code")
    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable String code){
        try {
            academicTitleService.delete(code);
            return new ResponseEntity<>("Academic Title successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "GET Academic Title by it's name")
    @GetMapping("/{academicTitle}")
    public ResponseEntity<?> finfByName(@PathVariable String academicTitle){
        try {
            AcademicTitleDTO acDTO = academicTitleConverter.toDTO(academicTitleService.findByName(academicTitle));
            return new ResponseEntity<>(acDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
