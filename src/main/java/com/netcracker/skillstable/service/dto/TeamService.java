package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
