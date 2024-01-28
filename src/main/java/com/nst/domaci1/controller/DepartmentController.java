package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.DepartmentConverter;
import com.nst.domaci1.converter.impl.ManagementOfDepartmentHistoryConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.dto.DepartmentSetManagerDTO;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import com.nst.domaci1.dto.ManagementOfDepartmentHistorySaveUpdateDTO;
import com.nst.domaci1.service.DepartmentService;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @Operation(summary = "PATCH set Manager for Department")
    @PatchMapping("/setCurrentManager/{departmentName}")
    public ResponseEntity<?> setManagerForDepartment(@PathVariable String departmentName, DepartmentSetManagerDTO dto) {
        try {
            Department department = departmentService.setManagerForDepartment(departmentName, dto);
            return new ResponseEntity<>(departmentConverter.toDTO(department), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////

    @Operation(summary = "GET ALL Management of Department History that belong to certain department")
    @GetMapping("managementOfDepartmentHistory/managersByDepartment/{departmentName}")
    public ResponseEntity<?> findAllManagersByDepartmentName(@PathVariable String departmentName) {
        List<ManagementOfDepartmentHistory> historyList;
        try {
            historyList = managementOfDepartmentHistoryService.findAllByDepartmentName(departmentName);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(managementOfDepartmentHistoryConverter.entitiesToDTOs(historyList), HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Management of Department History by Member ")
    @GetMapping("/managementOfDepartmentHistory/historyByMember/{memberId}")
    public ResponseEntity<?> findManagementHistoryByMemberIdAndDepartmentName(@PathVariable Long memberId) {
        List<ManagementOfDepartmentHistory> historyList;
        try {
            historyList = managementOfDepartmentHistoryService.findAllByMember(memberId);
            return new ResponseEntity<>(managementOfDepartmentHistoryConverter.entitiesToDTOs(historyList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "DELETE Management Of Department History by ID")
    @DeleteMapping("/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}")
    public ResponseEntity<String> deleteByIdManagementOfDepartmentHistory(@PathVariable Long managementOfDepartmentHistoryId) {
        try {
            managementOfDepartmentHistoryService.deleteById(managementOfDepartmentHistoryId);
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
            history = managementOfDepartmentHistoryService.findById(managementOfDepartmentHistoryId);
            return new ResponseEntity<>(managementOfDepartmentHistoryConverter.toDTO(history), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "GET latest Manager (Supervisor or Secretary) for Department")
    @GetMapping("/managementOfDepartmentHistory/lastManager/{departmentName}/{managerRole}")
    public ResponseEntity<?> getLatestManagerForDepartment(@PathVariable String departmentName, @PathVariable String managerRole) {
        try {
            ManagementOfDepartmentHistoryDTO mngmntDTO = managementOfDepartmentHistoryConverter.toDTO(managementOfDepartmentHistoryService.getLatestMangerOfDepartment(departmentName, managerRole));
            return new ResponseEntity<>(mngmntDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "PUT update Management Of Department History")
    @PutMapping("/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}")
    public ResponseEntity<?> updateManagementOfDepartmentHistory(@PathVariable Long managementOfDepartmentHistoryId, @Valid @RequestBody ManagementOfDepartmentHistorySaveUpdateDTO dto) {
        try {
            ManagementOfDepartmentHistoryDTO updatedDTO = managementOfDepartmentHistoryConverter.toDTO(managementOfDepartmentHistoryService.update(managementOfDepartmentHistoryId, dto));
            return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "POST create Management Of Department History")
    @PostMapping("/managementOfDepartmentHistory")
    public ResponseEntity<?> createManagementOfDepartmentHistory(@Valid @RequestBody ManagementOfDepartmentHistorySaveUpdateDTO dto) {
        try {
            ManagementOfDepartmentHistoryDTO createdDTO = managementOfDepartmentHistoryConverter.toDTO(managementOfDepartmentHistoryService.save(dto));
            return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
