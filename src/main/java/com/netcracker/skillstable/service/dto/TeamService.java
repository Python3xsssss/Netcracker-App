package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private UserService userService;

    public Integer createTeam(Team team) {
        EAVObject eavObj = new EAVObject(
                metamodelService.getEntityTypeByEntId(Team.getEntTypeId()),
                team.getName()
        );

        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(eavObj, Team.getAboutId(), team.getAbout()),
                new Parameter(eavObj, Team.getLeaderRefId(), team.getLeader().getId())
        )));

        List<Parameter> membersAsParams = new ArrayList<>();
        for (User member : team.getMembers()) {
            membersAsParams.add(new Parameter(eavObj, Team.getMemberRefId(), member.getId()));
        }
        eavObj.addParameters(membersAsParams);

        return eavService.createEAVObj(eavObj).getId();
    }

    public List<Team> getAllTeams() {
        return null; // todo
    }

    public Optional<Team> getTeamById(Integer teamId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(teamId);
        if (!optionalEavObj.isPresent() || !Team.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject teamEavObj = optionalEavObj.get();

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
                teamId,
                teamEavObj.getEntName(),
                teamEavObj.getParameterByAttrId(Team.getAboutId()).map(ParameterValue::getValueStr).orElse(null),
                leader,
                department,
                members
        ));
    }

    public void deleteTeam(Integer userId) {
        // todo
    }
}
