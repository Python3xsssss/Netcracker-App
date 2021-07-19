package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.*;
import com.netcracker.skillstable.model.dto.*;
import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserConverter {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;

    private static final Role[] roleValues = Role.values();
    private static final Position[] positionValues = Position.values();

    public EAVObject dtoToEavObj(User user, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                user.getUsername()
        );
        eavObj.setId(user.getId());

        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getRoleId()),
                        Role.STAFF.ordinal()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getFirstNameId()),
                        user.getFirstName()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getLastNameId()),
                        user.getLastName()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getAgeId()),
                        user.getAge()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getEmailId()),
                        user.getEmail()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getAboutId()),
                        user.getAbout()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getPositionId()),
                        (user.getPosition() != null) ? user.getPosition().ordinal() : Position.NEWCOMER.ordinal()
                )
        )));

        if (user.getDepartment() != null) {
            eavObj.addParameter(
                    new Parameter(
                            eavObj,
                            metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getDepartmentRefId()),
                            user.getDepartment().getId()
                    )
            );
        }
        if (user.getTeam() != null) {
            eavObj.addParameter(
                    new Parameter(
                            eavObj,
                            metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getTeamRefId()),
                            user.getTeam().getId()
                    )
            );
        }

        Attribute skillLevelAttr = metamodelService.updateEntTypeAttrMapping(entityType.getId(), User.getSkillLevelRefId());
        List<Parameter> skillLevelsAsParams = new ArrayList<>();
        for (SkillLevel skillLevel : user.getSkillLevels()) {
            skillLevelsAsParams.add(new Parameter(
                    eavObj,
                    skillLevelAttr,
                    skillLevel.getId()
            ));
        }
        eavObj.addParameters(skillLevelsAsParams);

        return eavObj;
    }

    public User eavObjToDto(EAVObject userEavObj) {
        User user = this.eavObjToDtoNoRefs(userEavObj);

        Optional<ParameterValue> departAsParam = userEavObj.getParameterByAttrId(User.getDepartmentRefId());
        Department department = new Department();
        if (departAsParam.isPresent()) {
            Optional<EAVObject> departEavObject = eavService.getEAVObjById(departAsParam.get().getValueInt());
            department = departEavObject.map(eavObject -> Department.builder()
                    .id(eavObject.getId())
                    .name(eavObject.getEntName())
                    .build()
            ).orElseGet(Department::new);
        }
        user.setDepartment(department);

        Optional<ParameterValue> teamAsParam = userEavObj.getParameterByAttrId(User.getTeamRefId());
        Team team = new Team();
        if (teamAsParam.isPresent()) {
            Optional<EAVObject> teamEavObject = eavService.getEAVObjById(teamAsParam.get().getValueInt());
            team = teamEavObject.map(eavObject -> Team.builder()
                    .id(eavObject.getId())
                    .name(eavObject.getEntName())
                    .build()
            ).orElseGet(Team::new);
        }
        user.setTeam(team);

        List<ParameterValue> skillsAsParams = userEavObj.getMultipleParametersByAttrId(User.getSkillLevelRefId());
        List<EAVObject> skillLevelsEavList = new ArrayList<>();
        for (ParameterValue skillAsParam : skillsAsParams) {
            Optional<EAVObject> optSkillEavObj = eavService.getEAVObjById(skillAsParam.getValueInt());
            optSkillEavObj.ifPresent(skillLevelsEavList::add);
        }
        Set<SkillLevel> skillLevels = new HashSet<>();
        for (EAVObject skillLevelAsEavObj : skillLevelsEavList) {
            Optional<ParameterValue> skillAsParam = userEavObj.getParameterByAttrId(SkillLevel.getSkillRefId());
            if (skillAsParam.isPresent()) {
                Optional<EAVObject> skillEavObject = eavService.getEAVObjById(skillAsParam.get().getValueInt());
                skillEavObject.ifPresent(eavObject -> skillLevels.add(SkillLevel.builder()
                        .id(skillLevelAsEavObj.getId())
                        .name(skillLevelAsEavObj.getEntName())
                        .level(skillLevelAsEavObj
                                .getParameterByAttrId(SkillLevel.getLevelId())
                                .map(ParameterValue::getValueInt)
                                .orElse(null)
                        )
                        .skill(SkillConverter.eavObjToDto(eavObject))
                        .build()
                ));
            }
        }
        user.setSkillLevels(skillLevels);

        return user;
    }

    public User eavObjToDtoNoRefs(EAVObject userEavObj) {
        Set<Role> roles = new HashSet<>();
        List<ParameterValue> rolesAsParams = userEavObj.getMultipleParametersByAttrId(User.getRoleId());
        for (ParameterValue roleParam : rolesAsParams) {
            roles.add(roleValues[Math.toIntExact(roleParam.getValueInt())]);
        }

        return User.builder()
                .id(userEavObj.getId())
                .username(userEavObj.getEntName())
                .password(null)
                .roles(roles)
                .firstName(userEavObj.getParameterByAttrId(User.getFirstNameId())
                        .map(ParameterValue::getValueStr)
                        .orElse(null))
                .lastName(userEavObj.getParameterByAttrId(User.getLastNameId())
                        .map(ParameterValue::getValueStr)
                        .orElse(null))
                .age(userEavObj.getParameterByAttrId(User.getAgeId())
                        .map(ParameterValue::getValueInt)
                        .orElse(null))
                .email(userEavObj.getParameterByAttrId(User.getEmailId())
                        .map(ParameterValue::getValueStr)
                        .orElse(null))
                .about(userEavObj.getParameterByAttrId(User.getAboutId())
                        .map(ParameterValue::getValueStr)
                        .orElse(null))
                .position(positionValues[
                        userEavObj
                                .getParameterByAttrId(User.getPositionId())
                                .map(ParameterValue::getValueInt)
                                .orElse(Position.NEWCOMER.ordinal())
                        ])
                .build();
    }
}
