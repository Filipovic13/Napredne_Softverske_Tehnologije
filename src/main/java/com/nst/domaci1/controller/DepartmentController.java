package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.DepartmentConverter;
import com.nst.domaci1.converter.impl.ManagementOfDepartmentHistoryConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
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
    private ManagementOfDepartmentHistoryConverter managementOfDepartmentHistoryConverter;

    public DepartmentController(DepartmentService departmentService, DepartmentConverter departmentConverter, ManagementOfDepartmentHistoryConverter managementOfDepartmentHistoryConverter) {
        this.departmentService = departmentService;
        this.departmentConverter = departmentConverter;
        this.managementOfDepartmentHistoryConverter = managementOfDepartmentHistoryConverter;
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

    @Operation(summary = "PATCH set Supervisor for Department")
    @PatchMapping("/supervisor/{memberId}/{departmentName}")
    public ResponseEntity<?> setSupervisorForDepartment(@PathVariable Long memberId, String departmentName) {
        try {
            ManagementOfDepartmentHistoryDTO mngmntDTO = managementOfDepartmentHistoryConverter.toDTO(departmentService.setSupervisorForDepartment(memberId, departmentName));
            return new ResponseEntity<>(mngmntDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "PATCH set Secretary for Department")
    @PatchMapping("/secretary/{memberId}/{departmentName}")
    public ResponseEntity<?> setSecretaryForDepartment(@PathVariable Long memberId, String departmentName) {
        try {
            ManagementOfDepartmentHistoryDTO mngmntDTO = managementOfDepartmentHistoryConverter.toDTO(departmentService.setSecretaryForDepartment(memberId, departmentName));
            return new ResponseEntity<>(mngmntDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Operation(summary = "GET ALL Management of Department History that belong to certain department")
    @GetMapping("managementHistoryByDepartment/{departmentName}")
    public ResponseEntity<?> findAllManagersByDepartmentName(@PathVariable String departmentName) {
        List<ManagementOfDepartmentHistory> historyList;
        try {
            historyList = departmentService.findAllManagersByDepartmentName(departmentName);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(managementOfDepartmentHistoryConverter.entitiesToDTOs(historyList), HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Management of Department History by Member and Department")
    @GetMapping("/membersManagementHistory/{memberId}/{departmentName}")
    public ResponseEntity<?> findManagementHistoryByMemberIdAndDepartmentName(@PathVariable Long memberId, @PathVariable String departmentName) {
        List<ManagementOfDepartmentHistory> historyList;
        try {
            historyList = departmentService.findAllByMemberAndDepartment(memberId, departmentName);
            return new ResponseEntity<>(managementOfDepartmentHistoryConverter.entitiesToDTOs(historyList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "DELETE Management Of Department History by ID")
    @DeleteMapping("/managementHistory/{managementOfDepartmentHistoryId}")
    public ResponseEntity<String> deleteManagementOfDepartmentHistory(@PathVariable Long managementOfDepartmentHistoryId) {
        try {
            departmentService.deleteManagementOfDepartmentHistory(managementOfDepartmentHistoryId);
            return new ResponseEntity<>("Management Of Department History successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "GET Management Of Department History by ID")
    @GetMapping("/managementHistory/{managementOfDepartmentHistoryId}")
    public ResponseEntity<?> findManagementOfDepartmentHistoryById(@PathVariable Long managementOfDepartmentHistoryId) {
        ManagementOfDepartmentHistory history;
        try {
             history = departmentService.findByIdManagementOfDepartmentHistory(managementOfDepartmentHistoryId);
            return new ResponseEntity<>(managementOfDepartmentHistoryConverter.toDTO(history), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
