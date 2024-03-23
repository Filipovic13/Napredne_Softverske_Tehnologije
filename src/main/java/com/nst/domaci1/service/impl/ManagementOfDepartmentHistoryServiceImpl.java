package com.nst.domaci1.service.impl;

import com.nst.domaci1.converter.impl.ManagementOfDepartmentHistoryConverter;
import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagerRole;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.ManagementOfDepartmentHistoryDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.ManagementOfDepartmentHistoryRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ManagementOfDepartmentHistoryServiceImpl implements ManagementOfDepartmentHistoryService {

    private final ManagementOfDepartmentHistoryRepository managementOfDepartmentHistoryRepository;
    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;
    private final ManagementOfDepartmentHistoryConverter managementOfDepartmentHistoryConverter;


    @Override
    public List<ManagementOfDepartmentHistoryDTO> findAllByDepartmentName(String departmentName) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }

        val allByDepartment = managementOfDepartmentHistoryRepository.
                findByDepartmentOrderByStartDateDesc(depDB.get());

        return managementOfDepartmentHistoryConverter.entitiesToDTOs(allByDepartment);
    }

    @Override
    public List<ManagementOfDepartmentHistoryDTO> findAllByMember(Long memberId) throws Exception {
        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        val allByMember = managementOfDepartmentHistoryRepository.
                findByMemberOrderByStartDateDesc(memDB.get());

        return managementOfDepartmentHistoryConverter.entitiesToDTOs(allByMember);
    }

    @Override
    public void deleteById(Long managementOfDepartmentHistoryId) throws Exception {
        Optional<ManagementOfDepartmentHistory> entity = managementOfDepartmentHistoryRepository.findById(managementOfDepartmentHistoryId);
        if (entity.isEmpty()) {
            throw new Exception("Management Of DepartmentHistory with the given ID doesn't exist!");
        }
        managementOfDepartmentHistoryRepository.delete(entity.get());
    }

    @Override
    public ManagementOfDepartmentHistoryDTO findById(Long managementOfDepartmentHistoryId) throws Exception {
        Optional<ManagementOfDepartmentHistory> mngmnt = managementOfDepartmentHistoryRepository.findById(managementOfDepartmentHistoryId);
        if (mngmnt.isEmpty()) {
            throw new Exception("Management Of DepartmentHistory with the given ID doesn't exist!");
        }
        return managementOfDepartmentHistoryConverter.toDTO(mngmnt.get());
    }

    @Override
    public ManagementOfDepartmentHistoryDTO getLatestMangerOfDepartment(String departmentName, String managerRole) throws Exception {
        Optional<Department> depDb = departmentRepository.findById(departmentName);
        if (depDb.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
        Department department = depDb.get();

        ManagerRole chosenRole;
        if (managerRole.equalsIgnoreCase("supervisor")) {
            chosenRole = ManagerRole.SUPERVISOR;
        } else if (managerRole.equalsIgnoreCase("secretary")) {
            chosenRole = ManagerRole.SECRETARY;
        } else {
            throw new Exception("Manger role can only be Supervisor or Secretary!");
        }

        Optional<ManagementOfDepartmentHistory> lastEndDateNull = managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleAndEndDateIsNullOrderByStartDateDesc(department, chosenRole);
        if (lastEndDateNull.isPresent()) {
            return managementOfDepartmentHistoryConverter.toDTO(lastEndDateNull.get());
        } else {
            Optional<ManagementOfDepartmentHistory> lastManager = managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleOrderByStartDateDesc(department, chosenRole);
            if (lastManager.isEmpty()) {
                throw new Exception("There is no " + chosenRole.toString() + " for department - " + departmentName);
            }
            return managementOfDepartmentHistoryConverter.toDTO(lastManager.get());
        }
    }

    @Override
    public ManagementOfDepartmentHistoryDTO save(ManagementOfDepartmentHistoryDTO dto) throws Exception {

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new Exception("End date must be after start date!");
        }

        ManagerRole chosenRole;
        if (dto.getManagerRole().equalsIgnoreCase("supervisor")) {
            chosenRole = ManagerRole.SUPERVISOR;
        } else if (dto.getManagerRole().equalsIgnoreCase("secretary")) {
            chosenRole = ManagerRole.SECRETARY;
        } else {
            throw new Exception("Manger role can only be Supervisor or Secretary!");
        }

        Optional<Member> memDB = memberRepository.findById(dto.getMemberId());
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        Optional<Department> depDB = departmentRepository.findById(dto.getDepartmentName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }


        val mngmnt = new ManagementOfDepartmentHistory(null, dto.getStartDate(), dto.getEndDate(), chosenRole, memDB.get(), depDB.get());
        val savedMngmnt =  managementOfDepartmentHistoryRepository.save(mngmnt);

        return managementOfDepartmentHistoryConverter.toDTO(savedMngmnt);
    }


    @Override
    public ManagementOfDepartmentHistoryDTO update(Long managementOfDepartmentHistoryId, ManagementOfDepartmentHistoryDTO dto) throws Exception {

        Optional<ManagementOfDepartmentHistory> mngmntDB = managementOfDepartmentHistoryRepository.findById(managementOfDepartmentHistoryId);
        if (mngmntDB.isEmpty()) {
            throw new Exception("Management Of Department History with the given ID doesn't exist!");
        }

        if (dto.getEndDate() != null && dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new Exception("End date must be after start date!");
        }

        ManagerRole chosenRole;
        if (dto.getManagerRole().equalsIgnoreCase("supervisor")) {
            chosenRole = ManagerRole.SUPERVISOR;
        } else if (dto.getManagerRole().equalsIgnoreCase("secretary")) {
            chosenRole = ManagerRole.SECRETARY;
        } else {
            throw new Exception("Manger role can only be Supervisor or Secretary!");
        }


        Optional<Member> memDB = memberRepository.findById(dto.getMemberId());
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        Optional<Department> depDB = departmentRepository.findById(dto.getDepartmentName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }


        ManagementOfDepartmentHistory mngmnt = managementOfDepartmentHistoryConverter.toEntity(dto);
//        ManagementOfDepartmentHistory mngmnt = mngmntDB.get();
//        mngmnt.setStartDate(dto.getStartDate());
//        mngmnt.setEndDate(dto.getEndDate());
//        mngmnt.setManagerRole(chosenRole);
//        mngmnt.setMember(memDB.get());
//        mngmnt.setDepartment(depDB.get());

        val savedMngmnt = managementOfDepartmentHistoryRepository.save(mngmnt);

        return managementOfDepartmentHistoryConverter.toDTO(savedMngmnt);
    }


}
