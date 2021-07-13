package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.DepartmentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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

    public Optional<Department> getDepartmentById(Integer departmentId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(departmentId);
        if (!optionalEavObj.isPresent() || !Department.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject departEavObj = optionalEavObj.get();

        return Optional.of(departmentConverter.eavObjToDto(departEavObj));
    }

    public void deleteDepartment(Integer departmentId) {
        Optional<Department> optionalDepartment = this.getDepartmentById(departmentId);
        if (!optionalDepartment.isPresent()) {
            return;
        }

        Department department = optionalDepartment.get();
        for (Team team : department.getTeams()) {
            eavService.deleteEAVObj(team.getId());
        }

        for (User user : department.getMembersNoTeam()) {
            user.setDepartment(null);
        }

        eavService.deleteEAVObj(departmentId);
    }
}
