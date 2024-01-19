package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.DepartmentConverter;
import com.nst.domaci1.converter.impl.ManagementOfDepartmentHistoryConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import com.nst.domaci1.service.DepartmentService;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService departmentService;
    private DepartmentConverter departmentConverter;
    private ManagementOfDepartmentHistoryService managementOfDepartmentHistoryService;
    private ManagementOfDepartmentHistoryConverter managementOfDepartmentHistoryConverter;

    public DepartmentController(DepartmentService departmentService, DepartmentConverter departmentConverter, ManagementOfDepartmentHistoryService managementOfDepartmentHistoryService, ManagementOfDepartmentHistoryConverter managementOfDepartmentHistoryConverter) {
        this.departmentService = departmentService;
        this.departmentConverter = departmentConverter;
        this.managementOfDepartmentHistoryService = managementOfDepartmentHistoryService;
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

///////////////////////////////////////////////////////////////////////////////////////////
    @Operation(summary = "PATCH set Supervisor for Department")
    @PatchMapping("/supervisor/{memberId}/{departmentName}")
    public ResponseEntity<?> setSupervisorForDepartment(@PathVariable Long memberId, @PathVariable String departmentName) {
        try {
            ManagementOfDepartmentHistoryDTO mngmntDTO = managementOfDepartmentHistoryConverter.toDTO(managementOfDepartmentHistoryService.setSupervisorForDepartment(memberId, departmentName));
            return new ResponseEntity<>(mngmntDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "PATCH set Secretary for Department")
    @PatchMapping("/secretary/{memberId}/{departmentName}")
    public ResponseEntity<?> setSecretaryForDepartment(@PathVariable Long memberId, @PathVariable String departmentName) {
        try {
            ManagementOfDepartmentHistoryDTO mngmntDTO = managementOfDepartmentHistoryConverter.toDTO(managementOfDepartmentHistoryService.setSecretaryForDepartment(memberId, departmentName));
            return new ResponseEntity<>(mngmntDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "GET ALL Management of Department History that belong to certain department")
    @GetMapping("managementOfDepartmentHistory/byDepartment/{departmentName}")
    public ResponseEntity<?> findAllManagersByDepartmentName(@PathVariable String departmentName) {
        List<ManagementOfDepartmentHistory> historyList;
        try {
            historyList = managementOfDepartmentHistoryService.findAllManagersByDepartmentName(departmentName);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(managementOfDepartmentHistoryConverter.entitiesToDTOs(historyList), HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Management of Department History by Member and Department")
    @GetMapping("/managementOfDepartmentHistory/{memberId}/{departmentName}")
    public ResponseEntity<?> findManagementHistoryByMemberIdAndDepartmentName(@PathVariable Long memberId, @PathVariable String departmentName) {
        List<ManagementOfDepartmentHistory> historyList;
        try {
            historyList = managementOfDepartmentHistoryService.findAllByMemberAndDepartment(memberId, departmentName);
            return new ResponseEntity<>(managementOfDepartmentHistoryConverter.entitiesToDTOs(historyList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "DELETE Management Of Department History by ID")
    @DeleteMapping("/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}")
    public ResponseEntity<String> deleteByIdManagementOfDepartmentHistory(@PathVariable Long managementOfDepartmentHistoryId) {
        try {
            managementOfDepartmentHistoryService.deleteByIdManagementOfDepartmentHistory(managementOfDepartmentHistoryId);
            return new ResponseEntity<>("Management Of Department History successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "GET Management Of Department History by ID")
    @GetMapping("/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}")
    public ResponseEntity<?> findByIdManagementOfDepartmentHistory(@PathVariable Long managementOfDepartmentHistoryId) {
        ManagementOfDepartmentHistory history;
        try {
            history = managementOfDepartmentHistoryService.findByIdManagementOfDepartmentHistory(managementOfDepartmentHistoryId);
            return new ResponseEntity<>(managementOfDepartmentHistoryConverter.toDTO(history), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "GET latest Manager (Supervisor or Secretary) for Department")
    @GetMapping("/latestManager/{departmentName}/{managerRole}")
    public ResponseEntity<?> getLatestSupervisor(@PathVariable String departmentName, @PathVariable String managerRole) {
        try {
            ManagementOfDepartmentHistoryDTO mngmntDTO = managementOfDepartmentHistoryConverter.toDTO(managementOfDepartmentHistoryService.getLatestMangerOfDepartment(departmentName, managerRole));
            return new ResponseEntity<>(mngmntDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
