package com.nst.domaci1.controller;

import com.nst.domaci1.converter.impl.ManagementOfDepartmentHistoryConverter;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagementOfDepartmentHistoryID;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.NotSupportedException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("managementOfDepartmentHistory")
public class ManagementOfDepartmentHistoryController {

    private ManagementOfDepartmentHistoryService managementServie;
    private ManagementOfDepartmentHistoryConverter mngmntConverter;

    public ManagementOfDepartmentHistoryController(ManagementOfDepartmentHistoryService managementServie, ManagementOfDepartmentHistoryConverter mngmntConverter) {
        this.managementServie = managementServie;
        this.mngmntConverter = mngmntConverter;
    }

    @Operation(summary = "SAVE new Management of Department History")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ManagementOfDepartmentHistoryDTO mngmntDTO) {
        ManagementOfDepartmentHistory mngmnt = mngmntConverter.toEntity(mngmntDTO);
        try {
            ManagementOfDepartmentHistoryDTO dto = mngmntConverter.toDTO(managementServie.save(mngmnt));
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "GET ALL Management of Department History")
    @GetMapping
    public ResponseEntity<List<ManagementOfDepartmentHistoryDTO>> getAll() {
        List<ManagementOfDepartmentHistoryDTO> list = mngmntConverter.entitiesToDTOs(managementServie.getAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "GET ALL Management of Department History - PAGEABLE")
    @GetMapping("/paging")
    public ResponseEntity<Page<ManagementOfDepartmentHistoryDTO>> getAllByPage(
            @RequestParam(name = "page" , defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "startDate") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection){
        Pageable pageable;
        if (sortDirection.equals("asc")){
            pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).ascending());
        }else {
            pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        }
        Page<ManagementOfDepartmentHistoryDTO> managementOfDepartmentHistoriesDTOPage = managementServie.getAll(pageable).map(mngmntConverter::toDTO);
        return new ResponseEntity<>(managementOfDepartmentHistoriesDTOPage, HttpStatus.OK);
    }

    @Operation(summary = "DELETE Management of Department History by start date and department name")
    @DeleteMapping("/{startDate}/{departmentName}")
    public ResponseEntity<?> delete(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                       @PathVariable String departmentName) {
        ManagementOfDepartmentHistoryID id = new ManagementOfDepartmentHistoryID(startDate, departmentName);
        try {
            managementServie.delete(id);
            return new ResponseEntity<>("ManagementOfDepartmentHistory successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "UPDATE  Management of Department History")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ManagementOfDepartmentHistoryDTO mngmntDepartmentDTO){
        ManagementOfDepartmentHistory mngmntDepartment = mngmntConverter.toEntity(mngmntDepartmentDTO);
        try {
            ManagementOfDepartmentHistoryDTO dto = mngmntConverter.toDTO(managementServie.update(mngmntDepartment));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "GET Management of Department History by start date and department name")
    @GetMapping("/{startDate}/{departmentName}")
    public ResponseEntity<?>  findById(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                     @PathVariable String departmentName) {
        ManagementOfDepartmentHistoryID id = new ManagementOfDepartmentHistoryID(startDate, departmentName);
        try {
            ManagementOfDepartmentHistoryDTO dto = mngmntConverter.toDTO(managementServie.findById(id));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "GET ALL Management of Department History that belong to certain department")
    @GetMapping("/{departmentName}")
    public ResponseEntity<?> findAllByDepartmentName(@PathVariable String departmentName) {
        List<ManagementOfDepartmentHistory> historyList;
        try {
            historyList = managementServie.findAllByDepartmentName(departmentName);
        } catch (Exception e) {
            return new ResponseEntity<>(">>> " + e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(mngmntConverter.entitiesToDTOs(historyList), HttpStatus.OK);
    }



}
