package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.DepartmentConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.NotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService departmentService;
    private DepartmentConverter departmentConverter;

    public DepartmentController(DepartmentService departmentService, DepartmentConverter departmentConverter) {
        this.departmentService = departmentService;
        this.departmentConverter = departmentConverter;
    }

    @Operation(summary = "SAVE new Department")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody DepartmentDTO departmentDTO) {
        Department d = departmentConverter.toEntity(departmentDTO);
        try {
            DepartmentDTO depDTO = departmentConverter.toDTO(departmentService.save(d));
            return new ResponseEntity<>(depDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "GET ALL Departments")
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        List<DepartmentDTO> list = departmentConverter.entitiesToDTOs(departmentService.getAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @Operation(summary = "DELETE Department by it's ID - name")
    @DeleteMapping("{name}")
    public ResponseEntity<String> delete(@PathVariable String name) {
        try {
            departmentService.delete(name);
            return new ResponseEntity<>("Department successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "GET Department by it's name")
    @GetMapping("/{name}")
    public ResponseEntity<?> findById(@PathVariable String name) {
        try {
            DepartmentDTO depDTO = departmentConverter.toDTO(departmentService.findById(name));
            return new ResponseEntity<>(depDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
