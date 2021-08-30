package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.OrgItem;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.eav.Attribute;
import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.model.eav.Parameter;
import com.netcracker.skillstable.service.dto.UserService;
import com.netcracker.skillstable.service.eav.EAVService;
import com.netcracker.skillstable.service.eav.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
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
    @Autowired
    private MetamodelService metamodelService;


    public EAVObject dtoToEavObj(Team team) {
        EAVObject eavObj = new EAVObject(
                metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId()),
                team.getName()
        );
        eavObj.setId(team.getId());

        if (team.getSuperior() == null || team.getSuperior().getId() == null) {
            throw new ValidationException("Validation failed: department is required!");
        }
        Department superior = new Department(team.getSuperior());
        eavObj.addParameters(new ArrayList<>(Arrays.asList(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(OrgItem.getEntTypeId(), OrgItem.getAboutId()),
                        team.getAbout()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(OrgItem.getEntTypeId(), OrgItem.getSuperiorRefId()),
                        eavService.getEAVObjById(superior.getId())
                )
        )));

        if (team.getLeader() != null && team.getLeader().getId() != null) {
            eavObj.addParameter(
                    new Parameter(
                            eavObj,
                            metamodelService.updateEntTypeAttrMapping(OrgItem.getEntTypeId(), OrgItem.getLeaderRefId()),
                            eavService.getEAVObjById(team.getLeader().getId())
                    )
            );
        }

        Attribute memberAttr = metamodelService.updateEntTypeAttrMapping(Team.getEntTypeId(), Team.getMemberRefId());
        List<Parameter> membersAsParams = new ArrayList<>();
        for (User member : team.getMembers()) {
            membersAsParams.add(new Parameter(
                    eavObj,
                    memberAttr,
                    eavService.getEAVObjById(member.getId())
            ));
        }
        eavObj.addParameters(membersAsParams);

        return eavObj;
    }

    public Team eavObjToDto(EAVObject teamEavObj) {
        Team team = this.eavObjToDtoNoRefs(teamEavObj);

        Optional<Parameter> leaderAsParam = teamEavObj.getParameterByAttrId(Team.getLeaderRefId());
        User leader = new User();
        if (leaderAsParam.isPresent()) {
            leader = userConverter.eavObjToDtoNoRefs(leaderAsParam.get().getReferenced());
        }
        team.setLeader(leader);

        Optional<Parameter> departAsParam = teamEavObj.getParameterByAttrId(Team.getSuperiorRefId());
        Department department = new Department();
        if (departAsParam.isPresent()) {
            department = departmentConverter.eavObjToDtoNoRefs(departAsParam.get().getReferenced());
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
        return new Team(
                teamEavObj.getId(),
                teamEavObj.getEntName(),
                teamEavObj
                        .getParameterByAttrId(Team.getAboutId())
                        .map(Parameter::getAttrValueTxt)
                        .orElse("")
        );
    }
}
