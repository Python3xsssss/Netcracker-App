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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;

    // todo: converter Department <-> EAVObject?
    public Integer createDepartment(Department department) {
        EAVObject eavObj = new EAVObject(
                metamodelService.getEntityTypeByEntTypeId(Department.getEntTypeId()),
                department.getName()
        );

        /*eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(eavObj, Department.getAboutId(), department.getAbout()),
                new Parameter(eavObj, Department.getLeaderRefId(), department.getLeader().getId())
        )));

        List<Parameter> teamsAsParams = new ArrayList<>();
        for (Team team : department.getTeams()) {
            teamsAsParams.add(new Parameter(eavObj, Department.getTeamRefId(), team.getId()));
        }
        eavObj.addParameters(teamsAsParams);*/

        return eavService.createEAVObj(eavObj).getId();
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

        Optional<ParameterValue> leaderAsParam = departEavObj.getParameterByAttrId(Department.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            Optional<EAVObject> leaderEavObject = eavService.getEAVObjById(leaderAsParam.get().getValueInt());
            leader = leaderEavObject.map(eavObject -> new User(
                    eavObject.getId(),
                    eavObject.getEntName()
            )).orElseGet(User::new);
        }

        List<ParameterValue> teamsAsParams = departEavObj.getMultipleParametersByAttrId(Department.getTeamRefId());
        List<EAVObject> teamsEavList = new ArrayList<>();
        for (ParameterValue teamAsParam : teamsAsParams) {
            Optional<EAVObject> optTeamEavObj = eavService.getEAVObjById(teamAsParam.getValueInt());
            optTeamEavObj.ifPresent(teamsEavList::add);
        }
        Set<Team> teams = new HashSet<>();
        for (EAVObject teamAsEavObj : teamsEavList) {
            teams.add(new Team(
                    teamAsEavObj.getId(),
                    teamAsEavObj.getEntName()
            ));
        }

        return Optional.of(new Department(
                departmentId,
                departEavObj.getEntName(),
                departEavObj.getParameterByAttrId(Department.getAboutId()).map(ParameterValue::getValueStr).orElse(null),
                leader,
                null,
                teams
        ));
    }

    public void deleteDepartment(Integer userId) {
        // todo
    }
}
