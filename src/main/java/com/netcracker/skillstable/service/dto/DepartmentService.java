package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.DepartmentConverter;
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
    private MetamodelService metamodelService;
    @Autowired
    private DepartmentConverter departmentConverter;


    public Department createDepartment(Department department) {
        return departmentConverter.eavObjToDto(eavService.createEAVObj(
                departmentConverter.dtoToEavObj(
                        department,
                        metamodelService.getEntityTypeByEntTypeId(Department.getEntTypeId())
                )
        ));
    }

    public List<Department> getAllDepartments() {
        return eavService
                .getAllByEntTypeId(Department.getEntTypeId())
                .stream()
                .map(departmentConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Department getDepartmentById(Integer departmentId) {
        return departmentConverter.eavObjToDto(eavService.getEAVObjById(departmentId));
    }

    public Department updateDepartment(Department department) {
        EAVObject updatedDepart = eavService.updateEAVObj(
                departmentConverter.dtoToEavObj(
                        department,
                        metamodelService.getEntityTypeByEntTypeId(Department.getEntTypeId())
                ),
                department.getId()
        );

        return departmentConverter.eavObjToDto(updatedDepart);
    }

    public void deleteDepartment(Integer departmentId) {
        Department department = this.getDepartmentById(departmentId);

        for (Team team : department.getTeams()) {
            eavService.deleteEAVObj(team.getId());
        }

        eavService.deleteEAVObj(departmentId);
    }
}
