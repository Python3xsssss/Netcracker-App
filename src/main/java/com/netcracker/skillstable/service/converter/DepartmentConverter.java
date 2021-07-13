package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
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

    public EAVObject dtoToEavObj(Department department, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                department.getName()
        );

        eavObj.addParameter(
                new Parameter(eavObj, Department.getAboutId(), department.getAbout())
        );

        if (department.getLeader() != null) {
            eavObj.addParameter(
                    new Parameter(eavObj, Department.getLeaderRefId(), department.getLeader().getId())
            );
        }

        List<Parameter> teamsAsParams = new ArrayList<>();
        for (Team team : department.getTeams()) {
            teamsAsParams.add(new Parameter(eavObj, Department.getTeamRefId(), team.getId()));
        }
        eavObj.addParameters(teamsAsParams);

        return eavObj;
    }

    public Department eavObjToDto(EAVObject departEavObj) {
        Optional<ParameterValue> leaderAsParam = departEavObj.getParameterByAttrId(Department.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            Optional<EAVObject> leaderEavObject = eavService.getEAVObjById(leaderAsParam.get().getValueInt());
            leader = leaderEavObject.map(eavObject -> User.builder()
                    .id(eavObject.getId())
                    .username(eavObject.getEntName())
                    .build()
            ).orElseGet(User::new);
        }

        Set<Team> teams = teamService
                .getAllTeams()
                .stream()
                .filter(team -> team.getSuperior().getId().equals(departEavObj.getId()))
                .collect(Collectors.toSet());

        for (Team team : teams) {
            team = Team.builder().id(team.getId()).name(team.getName()).build();
        }

        Set<User> membersNoTeam = userService
                .getAllUsers()
                .stream()
                .filter(user -> user.getTeam() == null && user.getDepartment().getId().equals(departEavObj.getId()))
                .collect(Collectors.toSet());

        for (User member : membersNoTeam) {
            member = User.builder().id(member.getId()).username(member.getUsername()).build();
        }

        return Department.builder()
                .id(departEavObj.getId())
                .name(departEavObj.getEntName())
                .about(departEavObj.getParameterByAttrId(Department.getAboutId()).map(ParameterValue::getValueStr).orElse(null))
                .leader(leader)
                .superior(null)
                .teams(teams)
                .membersNoTeam(membersNoTeam)
                .build();
    }
}
