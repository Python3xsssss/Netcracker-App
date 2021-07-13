package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.OrgItem;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamConverter {
    @Autowired
    private EAVService eavService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private UserConverter userConverter;

    public EAVObject dtoToEavObj(Team team, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                team.getName()
        );

        OrgItem superior = team.getSuperior();
        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(eavObj, Team.getAboutId(), team.getAbout()),
                new Parameter(eavObj, Team.getSuperiorRefId(), superior.getId())
        )));

        if (team.getLeader() != null) {
            eavObj.addParameter(
                    new Parameter(eavObj, Team.getLeaderRefId(), team.getLeader().getId())
            );
        }

        List<Parameter> membersAsParams = new ArrayList<>();
        for (User member : team.getMembers()) {
            membersAsParams.add(new Parameter(eavObj, Team.getMemberRefId(), member.getId()));
        }
        eavObj.addParameters(membersAsParams);

        return eavObj;
    }

    public Team eavObjToDto(EAVObject teamEavObj) {
        Team team = this.eavObjToDtoNoRefs(teamEavObj);

        Optional<ParameterValue> leaderAsParam = teamEavObj.getParameterByAttrId(Team.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            Optional<EAVObject> leaderEavObject = eavService.getEAVObjById(leaderAsParam.get().getValueInt());
            leader = leaderEavObject
                    .map(eavObject -> userConverter.eavObjToDtoNoRefs(eavObject))
                    .orElseGet(User::new);
        }
        team.setLeader(leader);

        Optional<ParameterValue> departAsParam = teamEavObj.getParameterByAttrId(Team.getSuperiorRefId());
        Department department = new Department();
        if (departAsParam.isPresent()) {
            Optional<EAVObject> departEavObject = eavService.getEAVObjById(departAsParam.get().getValueInt());
            department = departEavObject
                    .map(eavObject -> departmentConverter.eavObjToDtoNoRefs(eavObject))
                    .orElseGet(Department::new);
        }
        team.setSuperior(department);

        Set<User> members = userService
                .getAllUsers()
                .stream()
                .filter(user -> user.getTeam() != null && teamEavObj.getId().equals(user.getTeam().getId()))
                .map(User::toUserNoRefs)
                .collect(Collectors.toSet());
        team.setMembers(members);

        return team;
    }

    public Team eavObjToDtoNoRefs(EAVObject teamEavObj) {
        return Team.builder()
                .id(teamEavObj.getId())
                .name(teamEavObj.getEntName())
                .about(teamEavObj.getParameterByAttrId(Team.getAboutId()).map(ParameterValue::getValueStr).orElse(null))
                .build();
    }
}
