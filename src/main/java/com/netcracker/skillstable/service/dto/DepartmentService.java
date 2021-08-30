package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.exception.ResourceAlreadyExistsException;
import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.service.converter.DepartmentConverter;
import com.netcracker.skillstable.service.eav.EAVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DepartmentService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private DepartmentConverter departmentConverter;


    public Department createDepartment(Department department) {
        EAVObject departEavObj;
        try {
            departEavObj = eavService.createEAVObj(departmentConverter.dtoToEavObj(department));
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("Department '" + department.getName() + "' already exists!");
        }

        return departmentConverter.eavObjToDto(departEavObj);
    }

    public List<Department> getAllDepartments() {
        return eavService
                .getAllByEntTypeId(Department.getEntTypeId())
                .stream()
                .map(departmentConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Department getDepartmentById(Integer departmentId) {
        EAVObject departEavObj;
        try {
            departEavObj = eavService.getEAVObjById(departmentId);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("Department not found!");
        }

        return departmentConverter.eavObjToDto(departEavObj);
    }

    public Department updateDepartment(Department department) {
        EAVObject departEavObj;
        try {
            departEavObj = eavService.updateEAVObj(
                    departmentConverter.dtoToEavObj(department),
                    department.getId()
            );
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("Department '" + department.getName() + "' not found!");
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("Department '" + department.getName() + "' already exists!");
        }

        return departmentConverter.eavObjToDto(departEavObj);
    }

    public void deleteDepartment(Integer departmentId) {
        Department department = this.getDepartmentById(departmentId);

        for (Team team : department.getTeams()) {
            eavService.deleteEAVObj(team.getId());
        }

        eavService.deleteEAVObj(departmentId);
    }
}
