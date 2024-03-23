package com.nst.domaci1.controller;


import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.dto.DepartmentSetManagerDTO;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import com.nst.domaci1.service.DepartmentService;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ManagementOfDepartmentHistoryService managementOfDepartmentHistoryService;


    @Operation(summary = "SAVE new Department")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody DepartmentDTO dto) {
        try {
            DepartmentDTO depDTO = departmentService.save(dto);
            return new ResponseEntity<>(depDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "GET ALL Departments")
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        List<DepartmentDTO> list = departmentService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @Operation(summary = "DELETE Department by it's ID - name")
    @DeleteMapping("/{name}")
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
            DepartmentDTO depDTO = departmentService.findById(name);
            return new ResponseEntity<>(depDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "PATCH set Manager for Department")
    @PatchMapping("/setCurrentManager/{departmentName}")
    public ResponseEntity<?> setManagerForDepartment(@PathVariable String departmentName, @RequestBody DepartmentSetManagerDTO dto) {
        try {
            DepartmentDTO depDTO = departmentService.setManagerForDepartment(departmentName, dto);
            return new ResponseEntity<>(depDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////

    @Operation(summary = "GET ALL Management of Department History that belong to certain department")
    @GetMapping("/managementOfDepartmentHistory/managersByDepartment/{departmentName}")
    public ResponseEntity<?> findAllManagersByDepartmentName(@PathVariable String departmentName) {
        try {
            val historyList = managementOfDepartmentHistoryService.findAllByDepartmentName(departmentName);
            return new ResponseEntity<>(historyList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "GET ALL Management of Department History by Member ")
    @GetMapping("/managementOfDepartmentHistory/historyByMember/{memberId}")
    public ResponseEntity<?> findAllManagementHistoryByMemberId(@PathVariable Long memberId) {
        try {
            val historyList = managementOfDepartmentHistoryService.findAllByMember(memberId);
            return new ResponseEntity<>(historyList, HttpStatus.OK);
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
        try {
            val history = managementOfDepartmentHistoryService.findById(managementOfDepartmentHistoryId);
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "GET latest Manager (Supervisor or Secretary) for Department")
    @GetMapping("/managementOfDepartmentHistory/lastManager/{departmentName}/{managerRole}")
    public ResponseEntity<?> getLatestManagerForDepartment(@PathVariable String departmentName, @PathVariable String managerRole) {
        try {
            val mngmntDTO = managementOfDepartmentHistoryService.getLatestMangerOfDepartment(departmentName, managerRole);
            return new ResponseEntity<>(mngmntDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "PUT update Management Of Department History")
    @PutMapping("/managementOfDepartmentHistory/{managementOfDepartmentHistoryId}")
    public ResponseEntity<?> updateManagementOfDepartmentHistory(@PathVariable Long managementOfDepartmentHistoryId, @Valid @RequestBody ManagementOfDepartmentHistoryDTO dto) {
        try {
            val updatedDTO = managementOfDepartmentHistoryService.update(managementOfDepartmentHistoryId, dto);
            return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "POST create Management Of Department History")
    @PostMapping("/managementOfDepartmentHistory")
    public ResponseEntity<?> createManagementOfDepartmentHistory(@Valid @RequestBody ManagementOfDepartmentHistoryDTO dto) {
        try {
            val createdDTO = managementOfDepartmentHistoryService.save(dto);
            return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
