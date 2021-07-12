package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.Parameter;
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
    private EAVService eavService;

    public EAVObject dtoToEavObj(Team team, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                team.getName()
        );

        Department superior = (Department) team.getSuperior();
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

        List<ParameterValue> membersAsParams = teamEavObj.getMultipleParametersByAttrId(Team.getMemberRefId());
        List<EAVObject> membersEavList = new ArrayList<>();
        for (ParameterValue memberAsParam : membersAsParams) {
            Optional<EAVObject> optMemberEavObj = eavService.getEAVObjById(memberAsParam.getValueInt());
            optMemberEavObj.ifPresent(membersEavList::add);
        }
        Set<User> members = new HashSet<>();
        for (EAVObject memberAsEavObj : membersEavList) {
            members.add(User.builder()
                    .id(memberAsEavObj.getId())
                    .username(memberAsEavObj.getEntName())
                    .build()
            );
        }

        return Team.builder()
                .id(teamEavObj.getId())
                .name(teamEavObj.getEntName())
                .about(teamEavObj.getParameterByAttrId(Team.getAboutId()).map(ParameterValue::getValueStr).orElse(null))
                .leader(leader)
                .department(department)
                .members(members)
                .build();
    }
}
