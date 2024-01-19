package com.nst.domaci1.service.impl;

import com.nst.domaci1.domain.Department;
import com.nst.domaci1.repository.DepartmentRepository;
import com.nst.domaci1.repository.ManagementOfDepartmentHistoryRepository;
import com.nst.domaci1.repository.MemberRepository;
import com.nst.domaci1.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmnetServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private MemberRepository memberRepository;
    private ManagementOfDepartmentHistoryRepository managementOfDepartmentHistoryRepository;

    public DepartmnetServiceImpl(DepartmentRepository departmentRepository, MemberRepository memberRepository, ManagementOfDepartmentHistoryRepository managementOfDepartmentHistoryRepository) {
        this.departmentRepository = departmentRepository;
        this.memberRepository = memberRepository;
        this.managementOfDepartmentHistoryRepository = managementOfDepartmentHistoryRepository;
    }

    @Override
    public Department save(Department department) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(department.getName());
        if (depDB.isPresent()) {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        } else {
            return departmentRepository.save(department);
        }

    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public void delete(String name) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(name);
        if (depDB.isPresent()) {
            departmentRepository.delete(depDB.get());
        } else {
            throw new Exception("Department with the given name doesn't exist! \nEnter one of next values: \n" + departmentRepository.findAllNames());
        }
    }


    @Override
    public Department findById(String name) throws Exception {
        Optional<Department> depDB = departmentRepository.findById(name);
        if (depDB.isPresent()) {
            return depDB.get();
        } else {
            throw new Exception("Department with the given ID-name doesn't exist!");
        }

    }

}
