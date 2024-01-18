package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.Department;
import com.nst.domaci1.domain.ManagementOfDepartmentHistory;
import com.nst.domaci1.domain.ManagerRole;
import com.nst.domaci1.domain.Member;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.ManagementOfDepartmentHistoryRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public ManagementOfDepartmentHistory setSupervisorForDepartment(Long memberId, String departmentName) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist!");
        }
        Department department = depDB.get();

        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isPresent()) {
            Member newSupervisor = memDB.get();
            if (newSupervisor.getManagerRole() == ManagerRole.SUPERVISOR) {
                throw new Exception("This Member is already Supervisor of " + newSupervisor.getDepartment().getName() + " department");
            }
            newSupervisor.setManagerRole(ManagerRole.SUPERVISOR);
            Member updatedMemberRole = memberRepository.save(newSupervisor);

            Optional<ManagementOfDepartmentHistory> lastRecord = managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleOrderByStartDateDesc(department, ManagerRole.SUPERVISOR);
            if (lastRecord.isPresent()) {
                Member formerSupervisor = lastRecord.get().getMember();
                formerSupervisor.setManagerRole(ManagerRole.NONE);
                memberRepository.save(formerSupervisor);

                ManagementOfDepartmentHistory lastRecordToUpdate = lastRecord.get();
                lastRecordToUpdate.setEndDate(LocalDate.now());
                managementOfDepartmentHistoryRepository.save(lastRecordToUpdate);
            }

            return managementOfDepartmentHistoryRepository.save(new ManagementOfDepartmentHistory(null, LocalDate.now(), null, ManagerRole.SUPERVISOR, updatedMemberRole, department));
        } else {
            throw new Exception("Member with the given ID doesn't exist!");
        }
    }

    @Override
    public ManagementOfDepartmentHistory setSecretaryForDepartment(Long memberId, String departmentName) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist!");
        }
        Department department = depDB.get();

        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isPresent()) {
            Member newSecretary = memDB.get();
            if (newSecretary.getManagerRole() == ManagerRole.SECRETARY) {
                throw new Exception("This Member is already Secretary of " + newSecretary.getDepartment().getName() + " department");
            }
            newSecretary.setManagerRole(ManagerRole.SECRETARY);
            Member updatedMemberRole = memberRepository.save(newSecretary);

            Optional<ManagementOfDepartmentHistory> lastRecord = managementOfDepartmentHistoryRepository.findFirstByDepartmentAndManagerRoleOrderByStartDateDesc(department, ManagerRole.SECRETARY);
            if (lastRecord.isPresent()) {
                Member formerSecretary = lastRecord.get().getMember();
                formerSecretary.setManagerRole(ManagerRole.NONE);
                memberRepository.save(formerSecretary);

                ManagementOfDepartmentHistory lastRecordToUpdate = lastRecord.get();
                lastRecordToUpdate.setEndDate(LocalDate.now());
                managementOfDepartmentHistoryRepository.save(lastRecordToUpdate);
            }

            return managementOfDepartmentHistoryRepository.save(new ManagementOfDepartmentHistory(null, LocalDate.now(), null, ManagerRole.SECRETARY, updatedMemberRole, department));
        } else {
            throw new Exception("Member with the given ID doesn't exist!");
        }
    }

    @Override
    public List<ManagementOfDepartmentHistory> findAllManagersByDepartmentName(String departmentName) throws Exception {

        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist!");
        }

        return managementOfDepartmentHistoryRepository.findByDepartmentOrderByStartDateDesc(depDB.get());
    }

    @Override
    public List<ManagementOfDepartmentHistory> findAllByMemberAndDepartment(Long memberId, String departmentName) throws Exception {
        Optional<Member> memDB = memberRepository.findById(memberId);
        if (memDB.isEmpty()) {
            throw new Exception("Member with the given ID doesn't exist!");
        }

        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()) {
            throw new Exception("Department with the given name doesn't exist!");
        }

        return managementOfDepartmentHistoryRepository.findByMemberAndDepartmentOrderByStartDateDesc(memDB.get(), depDB.get());

    }

    @Override
    public void deleteManagementOfDepartmentHistory(Long managementOfDepartmentHistoryId) throws Exception {
        Optional<ManagementOfDepartmentHistory> entity = managementOfDepartmentHistoryRepository.findById(managementOfDepartmentHistoryId);
        if (entity.isPresent()) {
            managementOfDepartmentHistoryRepository.delete(entity.get());
        } else {
            throw new Exception("Management Of DepartmentHistory with the given ID doesn't exist!");
        }
    }

    @Override
    public ManagementOfDepartmentHistory findByIdManagementOfDepartmentHistory(Long managementOfDepartmentHistoryId) throws Exception {
        Optional<ManagementOfDepartmentHistory> entity = managementOfDepartmentHistoryRepository.findById(managementOfDepartmentHistoryId);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new Exception("Management Of DepartmentHistory with the given ID doesn't exist!");
        }
    }

}
