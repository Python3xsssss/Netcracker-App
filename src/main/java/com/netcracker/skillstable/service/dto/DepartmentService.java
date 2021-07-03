package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.attr.Role;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private TeamService teamService;

    public Long createDepartment(Department department) {
        EAVObject eavObj = new EAVObject(
                metamodelService.getEntityTypeByEntId(Department.getEntTypeId()),
                department.getName()
        );

        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(eavObj, Department.getAboutId(), department.getAbout()),
                new Parameter(eavObj, Department.getLeaderRefId(), department.getLeader().getId())
        )));

        List<Parameter> teamsAsParams = new ArrayList<>();
        for (Team team : department.getTeams()) {
            teamsAsParams.add(new Parameter(eavObj, Department.getTeamRefId(), team.getId()));
        }
        eavObj.addParameters(teamsAsParams);

        return eavService.createEAVObj(eavObj).getId();
    }

    public List<Department> getAllDepartments() {
        return null; // todo
    }

    public Optional<Department> getUserById(Long departmentId) {
        Optional<EAVObject> eavObj = eavService.getEAVObjById(departmentId);
        if (eavObj.isPresent()) {
            // todo
        }
        return Optional.empty();
    }

    public void deleteUser(Long userId) {
        // todo
    }
}
