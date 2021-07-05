package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.DepartmentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;

    
    public Integer createDepartment(Department department) {
        return eavService.createEAVObj(
                DepartmentConverter.dtoToEavObj(
                        department,
                        metamodelService.getEntityTypeByEntTypeId(Department.getEntTypeId())
                )
        ).getId();
    }

    public List<Department> getAllDepartments() {
        return null; // todo
    }

    public Optional<Department> getDepartmentById(Integer departmentId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(departmentId);
        if (!optionalEavObj.isPresent() || !Department.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject departEavObj = optionalEavObj.get();

        return DepartmentConverter.eavObjToDto(departEavObj);
    }

    public void deleteDepartment(Integer userId) {
        // todo
    }
}
