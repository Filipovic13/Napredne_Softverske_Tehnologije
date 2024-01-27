package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagerRole;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.dto.ManagementOfDepartmentHistorySaveUpdateDTO;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.ManagementOfDepartmentHistoryRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagementOfDepartmentHistoryServiceImpl implements ManagementOfDepartmentHistoryService {

    private ManagementOfDepartmentHistoryRepository managementOfDepartmentHistoryRepository;
    private DepartmentRepository departmentRepository;
    private MemberRepository memberRepository;

    public ManagementOfDepartmentHistoryServiceImpl(ManagementOfDepartmentHistoryRepository managementOfDepartmentHistoryRepository, DepartmentRepository departmentRepository, MemberRepository memberRepository) {
        this.managementOfDepartmentHistoryRepository = managementOfDepartmentHistoryRepository;
        this.departmentRepository = departmentRepository;
        this.memberRepository = memberRepository;
    }



    @Override
    public List<ManagementOfDepartmentHistory> findAllByDepartmentName(String departmentName) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
        return managementOfDepartmentHistoryRepository.findByDepartmentOrderByStartDateDesc(depDB.get());
    }

    @Override
    public List<ManagementOfDepartmentHistory> findAllByMember(Long memberId) throws Exception {
        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        return managementOfDepartmentHistoryRepository.findByMemberOrderByStartDateDesc(memDB.get());
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
    public ManagementOfDepartmentHistory findById(Long managementOfDepartmentHistoryId) throws Exception {
        Optional<ManagementOfDepartmentHistory> entity = managementOfDepartmentHistoryRepository.findById(managementOfDepartmentHistoryId);
        if (entity.isEmpty()) {
            throw new Exception("Management Of DepartmentHistory with the given ID doesn't exist!");
        }
        return entity.get();
    }

    @Override
    public ManagementOfDepartmentHistory getLatestMangerOfDepartment(String departmentName, String managerRole) throws Exception {
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

        Optional<ManagementOfDepartmentHistory> lastManager = managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleOrderByStartDateDesc(department, chosenRole);
        if (lastManager.isEmpty()) {
            throw new Exception("There is no " + chosenRole.toString() + " for department - " + departmentName);
        }
        return lastManager.get();
    }

    @Override
    public ManagementOfDepartmentHistory save(ManagementOfDepartmentHistorySaveUpdateDTO dto) throws Exception {

        if (dto.getEndDate().isBefore(dto.getStartDate())){
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
        if (memDB.get().getManagerRole() == ManagerRole.SUPERVISOR) {
            throw new Exception("This member is already a Supervisor!");
        }
        if (memDB.get().getManagerRole() == ManagerRole.SECRETARY) {
            throw new Exception("This member is already a Secretary!");
        }

        Optional<Department> depDB = departmentRepository.findById(dto.getDepartmentName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }


        ManagementOfDepartmentHistory mngmnt = new ManagementOfDepartmentHistory(null, dto.getStartDate(), dto.getEndDate(), chosenRole, memDB.get(), depDB.get());
        return managementOfDepartmentHistoryRepository.save(mngmnt);
    }



    @Override
    public ManagementOfDepartmentHistory update(Long managementOfDepartmentHistoryId, ManagementOfDepartmentHistorySaveUpdateDTO dto) throws Exception {

        Optional<ManagementOfDepartmentHistory> mngmntDB = managementOfDepartmentHistoryRepository.findById(managementOfDepartmentHistoryId);
        if (mngmntDB.isEmpty()){
            throw new Exception("Management Of Department History with the given ID doesn't exist!");
        }

        if (dto.getEndDate().isBefore(dto.getStartDate())){
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
        if (memDB.get().getManagerRole() == ManagerRole.SUPERVISOR) {
            throw new Exception("This member is already a Supervisor!");
        }
        if (memDB.get().getManagerRole() == ManagerRole.SECRETARY) {
            throw new Exception("This member is already a Secretary!");
        }

        Optional<Department> depDB = departmentRepository.findById(dto.getDepartmentName());
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }


        ManagementOfDepartmentHistory mngmnt = mngmntDB.get();
        mngmnt.setStartDate(dto.getStartDate());
        mngmnt.setEndDate(dto.getEndDate());
        mngmnt.setManagerRole(chosenRole);
        mngmnt.setMember(memDB.get());
        mngmnt.setDepartment(depDB.get());

        return managementOfDepartmentHistoryRepository.save(mngmnt);
    }


}
