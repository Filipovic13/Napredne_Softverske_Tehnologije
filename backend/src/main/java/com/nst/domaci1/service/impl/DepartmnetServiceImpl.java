package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.DepartmentConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagerRole;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.DepartmentDTO;
import com.nst.domaci1.dto.DepartmentSetManagerDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.ManagementOfDepartmentHistoryRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmnetServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;
    private final ManagementOfDepartmentHistoryRepository managementOfDepartmentHistoryRepository;
    private final DepartmentConverter departmentConverter;


    @Override
    public DepartmentDTO save(DepartmentDTO dto) throws Exception {

        Optional<Department> depDB = departmentRepository.findById(dto.getName());
        if (depDB.isPresent()) {
            throw new Exception("Department with the given name already exists!");
        }
        val department = departmentConverter.toEntity(dto);

        val savedDepartment = departmentRepository.save(department);

        return departmentConverter.toDTO(savedDepartment);
    }

    @Override
    public List<DepartmentDTO> getAll() {
        return departmentConverter.entitiesToDTOs(departmentRepository.findAll());
    }

    @Override
    public void delete(String name) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(name);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
        departmentRepository.delete(depDB.get());
    }


    @Override
    public DepartmentDTO findById(String name) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(name);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
        return departmentConverter.toDTO(depDB.get());
    }

    @Override
    @Transactional
    public DepartmentDTO setManagerForDepartment(String departmentName, DepartmentSetManagerDTO dto) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
        Department department = depDB.get();

        ManagerRole role;
        if (dto.getManagerRole().equalsIgnoreCase("supervisor")){
            role = ManagerRole.SUPERVISOR;
        } else if (dto.getManagerRole().equalsIgnoreCase("secretary")) {
            role = ManagerRole.SECRETARY;
        }else {
            throw new Exception("Manager role can only be Supervisor or Secretary!");
        }

        Optional<Member> memDB = memberRepository.findById(dto.getMemberId());
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }


        Optional<ManagementOfDepartmentHistory> lastRecord = managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleOrderByStartDateDesc(department, role);
        if (lastRecord.isPresent() && lastRecord.get().getEndDate() == null) {
            ManagementOfDepartmentHistory lastRecordToUpdate = lastRecord.get();
            lastRecordToUpdate.setEndDate(LocalDate.now());
            managementOfDepartmentHistoryRepository.save(lastRecordToUpdate);
        }


        if (role == ManagerRole.SUPERVISOR){
            department.setSupervisor(memDB.get());
        }else{
            department.setSecretary(memDB.get());
        }
        managementOfDepartmentHistoryRepository.save(new ManagementOfDepartmentHistory(null, LocalDate.now(), null, role, memDB.get(), department));

        val savedDepartment =  departmentRepository.save(department);

        return departmentConverter.toDTO(savedDepartment);
    }

}
