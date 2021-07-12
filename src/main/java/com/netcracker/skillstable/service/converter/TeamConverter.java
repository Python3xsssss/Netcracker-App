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
        Optional<ParameterValue> leaderAsParam = teamEavObj.getParameterByAttrId(Team.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            Optional<EAVObject> leaderEavObject = eavService.getEAVObjById(leaderAsParam.get().getValueInt());
            leader = leaderEavObject.map(eavObject -> User.builder()
                            .id(eavObject.getId())
                            .username(eavObject.getEntName())
                            .build()
            ).orElseGet(User::new);
        }

        Optional<ParameterValue> departAsParam = teamEavObj.getParameterByAttrId(Team.getSuperiorRefId());
        Department department = new Department();
        if (departAsParam.isPresent()) {
            Optional<EAVObject> departEavObject = eavService.getEAVObjById(departAsParam.get().getValueInt());
            department = departEavObject.map(eavObject -> Department.builder()
                    .id(eavObject.getId())
                    .name(eavObject.getEntName())
                    .build()
            ).orElseGet(Department::new);
        }

        Set<User> members = userService
                .getAllUsers()
                .stream()
                .filter(user -> user.getTeam().getId().equals(teamEavObj.getId()))
                .collect(Collectors.toSet());

        for (User member : members) {
            member = User.builder().id(member.getId()).username(member.getUsername()).build();
        }

        return Team.builder()
                .id(teamEavObj.getId())
                .name(teamEavObj.getEntName())
                .about(teamEavObj.getParameterByAttrId(Team.getAboutId()).map(ParameterValue::getValueStr).orElse(null))
                .leader(leader)
                .superior(department)
                .members(members)
                .build();
    }
}
