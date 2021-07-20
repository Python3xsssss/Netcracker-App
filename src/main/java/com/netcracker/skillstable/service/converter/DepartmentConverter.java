package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.*;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.dto.TeamService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentConverter {
    @Autowired
    private EAVService eavService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private MetamodelService metamodelService;

    public EAVObject dtoToEavObj(Department department, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                department.getName()
        );

        eavObj.addParameter(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), Department.getAboutId()),
                        department.getAbout()
                )
        );

        if (department.getLeader() != null && department.getLeader().getId() != null) {
            eavObj.addParameter(
                    new Parameter(
                            eavObj,
                            metamodelService.updateEntTypeAttrMapping(entityType.getId(), Department.getLeaderRefId()),
                            department.getLeader().getId()
                    )
            );
        }

        Attribute teamAttr = metamodelService.updateEntTypeAttrMapping(entityType.getId(), Department.getLeaderRefId());
        List<Parameter> teamsAsParams = new ArrayList<>();
        for (Team team : department.getTeams()) {
            teamsAsParams.add(
                    new Parameter(
                            eavObj,
                            teamAttr,
                            team.getId()
                    )
            );
        }
        eavObj.addParameters(teamsAsParams);

        return eavObj;
    }

    public Department eavObjToDto(EAVObject departEavObj) {
        Department department = this.eavObjToDtoNoRefs(departEavObj);

        Optional<ParameterValue> leaderAsParam = departEavObj.getParameterByAttrId(Department.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            Optional<EAVObject> leaderEavObject = eavService.getEAVObjById(leaderAsParam.get().getValueInt());
            leader = leaderEavObject
                    .map(eavObject -> userConverter.eavObjToDtoNoRefs(eavObject))
                    .orElseGet(User::new);
        }
        department.setLeader(leader);

        Set<Team> teams = teamService
                .getAllTeams()
                .stream()
                .filter(team -> departEavObj.getId().equals(team.getSuperior().getId()))
                .map(Team::toTeamNoRefs)
                .collect(Collectors.toSet());
        department.setTeams(teams);

        Set<User> membersNoTeam = userService
                .getAllUsers()
                .stream()
                .filter(
                        user -> (user.getTeam() == null ||
                                user.getTeam().getId() == null) &&
                                user.getDepartment() != null &&
                                departEavObj.getId().equals(user.getDepartment().getId())
                )
                .map(User::toUserNoRefs)
                .collect(Collectors.toSet());
        department.setMembersNoTeam(membersNoTeam);

        return department;
    }

    public Department eavObjToDtoNoRefs(EAVObject departEavObj) {
        return Department.builder()
                .id(departEavObj.getId())
                .name(departEavObj.getEntName())
                .about(departEavObj.getParameterByAttrId(Department.getAboutId())
                        .map(ParameterValue::getValueStr)
                        .orElse(null))
                .build();
    }
}
