package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.ScientificFieldConverter;
import com.nst.domaci1.domain.ScientificField;
import com.nst.domaci1.dto.ScientificFieldDTO;
import com.nst.domaci1.service.ScientificFieldService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scientificField")
public class ScientificFieldController {

    private ScientificFieldService scientificFieldService;
    private ScientificFieldConverter scientificFieldConverter;

    public ScientificFieldController(ScientificFieldService scientificFieldService, ScientificFieldConverter scientificFieldConverter) {
        this.scientificFieldService = scientificFieldService;
        this.scientificFieldConverter = scientificFieldConverter;
    }

    @Operation(summary = "SAVE new Scientific Field")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ScientificFieldDTO scientificFieldDTO) {
        ScientificField sf = scientificFieldConverter.toEntity(scientificFieldDTO);
        try {
            ScientificFieldDTO scDTO = scientificFieldConverter.toDTO(scientificFieldService.save(sf));
            return new ResponseEntity<>(scDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "GET ALL Scientific Fields")
    @GetMapping
    public ResponseEntity<List<ScientificFieldDTO>> getAll() {
        List<ScientificFieldDTO> list = scientificFieldConverter.entitiesToDTOs(scientificFieldService.getAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "DELETE Scientific Field by it's ID - code")
    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable String code) {
        try {
            scientificFieldService.delete(code);
            return new ResponseEntity<>("Scientific Field successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "GET Scientific Field by name")
    @GetMapping("/{scientificField}")
    public ResponseEntity<?> findByName(@PathVariable String scientificField) {
        try {
            ScientificFieldDTO sfDTO = scientificFieldConverter.toDTO(scientificFieldService.findByName(scientificField));
            return new ResponseEntity<>(sfDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
