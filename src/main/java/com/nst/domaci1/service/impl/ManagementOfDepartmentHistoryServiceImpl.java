package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.*;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.ManagementOfDepartmentHistoryRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.service.ManagementOfDepartmentHistoryService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ManagementOfDepartmentHistoryServiceImpl implements ManagementOfDepartmentHistoryService {

    private ManagementOfDepartmentHistoryRepository managementHistoryRepository;
    private MemberRepository memberRepository;
    private DepartmentRepository departmentRepository;

    public ManagementOfDepartmentHistoryServiceImpl(ManagementOfDepartmentHistoryRepository managementHistoryRepository, MemberRepository memberRepository, DepartmentRepository departmentRepository) {
        this.managementHistoryRepository = managementHistoryRepository;
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public ManagementOfDepartmentHistory save(ManagementOfDepartmentHistory entity) throws Exception {

        Optional<ManagementOfDepartmentHistory> lastRecord = managementHistoryRepository.findFirstByDepartmentNameOrderByStartDateDesc(entity.getDepartment().getName());
        if (lastRecord.isPresent() && entity.getStartDate().isBefore(lastRecord.get().getEndDate())) {
            throw new Exception("Start date isn't after last end date (" + lastRecord.get().getEndDate() + ") for the given department!");
        } else {

            Optional<Member> supervisorDB = memberRepository.findById(new MemberID(entity.getSupervisorFirstName(), entity.getSupervisorLastName()));
            Optional<Member> secretaryDB = memberRepository.findById(new MemberID(entity.getSecretaryFirstName(), entity.getSecretaryLastName()));
            Optional<Department> depDB =  departmentRepository.findById(entity.getDepartment().getName());
            if (supervisorDB.isEmpty()) {
                throw new Exception("Member with the given name and lastname for Supervisor don't exist!");
            } else if (secretaryDB.isEmpty()) {
                throw new Exception("Member with the given name and lastname for Secretary don't exist!");
            } else if (depDB.isEmpty()) {
                throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
            }

            if (supervisorDB.get().getManagerRole() == ManagerRole.SUPERVISOR){
                throw new Exception("Entered Member for Supervisor, already has that role!");
            }

            if (secretaryDB.get().getManagerRole() == ManagerRole.SECRETARY){
                throw new Exception("Entered Member for Secretary, already has that role!");
            }


            if (lastRecord.isPresent()){
                Member formerSupervisor = (memberRepository.findById(new MemberID(lastRecord.get().getSupervisorFirstName(), lastRecord.get().getSupervisorLastName())).get());
                Member formerSecretary = (memberRepository.findById(new MemberID(lastRecord.get().getSecretaryFirstName(), lastRecord.get().getSecretaryLastName()))).get();

                //update manager role of former manager to NONE
                formerSupervisor.setManagerRole(ManagerRole.NONE);
                formerSecretary.setManagerRole(ManagerRole.NONE);

                //save changes
                memberRepository.save(formerSupervisor);
                memberRepository.save(formerSecretary);
            }


            // set manager roles to new managers
            Member newSupervisor = supervisorDB.get();
            newSupervisor.setManagerRole(ManagerRole.SUPERVISOR);

            Member newSecretary = secretaryDB.get();
            newSecretary.setManagerRole(ManagerRole.SECRETARY);

            memberRepository.save(newSupervisor);
            memberRepository.save(newSecretary);


            entity.setSupervisorFirstName(newSupervisor.getFirstName());
            entity.setSupervisorLastName(newSupervisor.getLastName());
            entity.setDepartment(depDB.get());

            return managementHistoryRepository.save(entity);
        }

    }

    @Override
    public List<ManagementOfDepartmentHistory> getAll() {
        return managementHistoryRepository.findAll();
    }

    @Override
    public Page<ManagementOfDepartmentHistory> getAll(Pageable pageable) {
        return managementHistoryRepository.findAll(pageable);
    }

    @Override
    public void delete(ManagementOfDepartmentHistoryID id) throws Exception {
        Optional<ManagementOfDepartmentHistory> entity = managementHistoryRepository.findById(id);
        if (entity.isPresent()) {
            managementHistoryRepository.delete(entity.get());
        } else {
            throw new Exception("Instance with the given start date and department doesn't exist!");
        }
    }

    @Override
    public ManagementOfDepartmentHistory update(ManagementOfDepartmentHistory managementHistory) throws Exception {
        ManagementOfDepartmentHistoryID id = new ManagementOfDepartmentHistoryID(managementHistory.getStartDate(), managementHistory.getDepartment().getName());

        Optional<ManagementOfDepartmentHistory> mngmntHistoruDB = managementHistoryRepository.findById(id);
        if (mngmntHistoruDB.isEmpty()){
            throw new Exception("Management of Department doesn't exist for the given Start Date and given Department name");
        }

        return managementHistoryRepository.save(managementHistory);
    }

    @Override
    public ManagementOfDepartmentHistory findById(ManagementOfDepartmentHistoryID id) throws Exception {
        Optional<ManagementOfDepartmentHistory> entity = managementHistoryRepository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new Exception("Instance with the given start date and department doesn't exist!");
        }
    }

    @Override
    public List<ManagementOfDepartmentHistory> findAllByDepartmentName(String departmentName) throws Exception {

        Optional<Department> depDB = departmentRepository.findById(departmentName);
        if (depDB.isEmpty()){
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
        return managementHistoryRepository.findByDepartmentName(departmentName);
    }
}
