package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.*;
import com.netcracker.skillstable.model.dto.Department;
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
    private TeamConverter teamConverter;
    @Autowired
    private MetamodelService metamodelService;


    public EAVObject dtoToEavObj(Department department) {
        final EntityType depEntityType = metamodelService.getEntityTypeByEntTypeId(Department.getEntTypeId());
        EAVObject eavObj = new EAVObject(
                depEntityType,
                department.getName()
        );
        eavObj.setId(department.getId());

        eavObj.addParameter(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(depEntityType.getId(), Department.getAboutId()),
                        department.getAbout()
                )
        );

        if (department.getLeader() != null && department.getLeader().getId() != null) {
            eavObj.addParameter(
                    new Parameter(
                            eavObj,
                            metamodelService.updateEntTypeAttrMapping(depEntityType.getId(), Department.getLeaderRefId()),
                            eavService.getEAVObjById(department.getLeader().getId())
                    )
            );
        }

        Attribute teamAttr = metamodelService.updateEntTypeAttrMapping(depEntityType.getId(), Department.getTeamRefId());
        List<Parameter> teamsAsParams = new ArrayList<>();
        for (Team team : department.getTeams()) {
            teamsAsParams.add(
                    new Parameter(
                            eavObj,
                            teamAttr,
                            eavService.getEAVObjById(team.getId())
                    )
            );
        }
        eavObj.addParameters(teamsAsParams);

        return eavObj;
    }

    public Department eavObjToDto(EAVObject departEavObj) {
        Department department = this.eavObjToDtoNoRefs(departEavObj);

        Optional<Parameter> leaderAsParam = departEavObj.getParameterByAttrId(Department.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            leader = userConverter.eavObjToDtoNoRefs(leaderAsParam.get().getReferenced());
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
        return new Department(
                departEavObj.getId(),
                departEavObj.getEntName(),
                departEavObj
                        .getParameterByAttrId(Department.getAboutId())
                        .map(Parameter::getAttrValueTxt)
                        .orElse("")
        );
    }
}
