package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentConverter {
    @Autowired
    private static EAVService eavService;

    public static EAVObject dtoToEavObj(Department department, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
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
        return eavObj;
    }

    public static Optional<Department> eavObjToDto(EAVObject departEavObj) {
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
                departEavObj.getId(),
                departEavObj.getEntName(),
                departEavObj.getParameterByAttrId(Department.getAboutId()).map(ParameterValue::getValueStr).orElse(null),
                leader,
                null,
                teams
        ));
    }
}
