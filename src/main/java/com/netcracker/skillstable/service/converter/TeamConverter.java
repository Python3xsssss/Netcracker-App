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
public class TeamConverter {
    @Autowired
    private static EAVService eavService;

    public static EAVObject dtoToEavObj(Team team, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                team.getName()
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

    public static Optional<Team> eavObjToDto(EAVObject teamEavObj) {
        Optional<ParameterValue> leaderAsParam = teamEavObj.getParameterByAttrId(Team.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            Optional<EAVObject> leaderEavObject = eavService.getEAVObjById(leaderAsParam.get().getValueInt());
            leader = leaderEavObject.map(eavObject -> new User(
                    eavObject.getId(),
                    eavObject.getEntName()
            )).orElseGet(User::new);
        }

        Optional<ParameterValue> departAsParam = teamEavObj.getParameterByAttrId(Team.getSuperiorRefId());
        Department department = new Department();
        if (departAsParam.isPresent()) {
            Optional<EAVObject> departEavObject = eavService.getEAVObjById(departAsParam.get().getValueInt());
            department = departEavObject.map(eavObject -> new Department(
                    eavObject.getId(),
                    eavObject.getEntName()
            )).orElseGet(Department::new);
        }

        List<ParameterValue> membersAsParams = teamEavObj.getMultipleParametersByAttrId(Team.getMemberRefId());
        List<EAVObject> membersEavList = new ArrayList<>();
        for (ParameterValue memberAsParam : membersAsParams) {
            Optional<EAVObject> optMemberEavObj = eavService.getEAVObjById(memberAsParam.getValueInt());
            optMemberEavObj.ifPresent(membersEavList::add);
        }
        Set<User> members = new HashSet<>();
        for (EAVObject memberAsEavObj : membersEavList) {
            members.add(new User(
                    memberAsEavObj.getId(),
                    memberAsEavObj.getEntName()
            ));
        }

        return Optional.of(new Team(
                teamEavObj.getId(),
                teamEavObj.getEntName(),
                teamEavObj.getParameterByAttrId(Team.getAboutId()).map(ParameterValue::getValueStr).orElse(null),
                leader,
                department,
                members
        ));
    }
}
