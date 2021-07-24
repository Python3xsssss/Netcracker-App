package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.*;
import com.netcracker.skillstable.model.dto.*;
import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserConverter {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private SkillConverter skillConverter;
    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private TeamConverter teamConverter;

    private static final Role[] roleValues = Role.values();
    private static final Position[] positionValues = Position.values();


    public EAVObject skillLevelToEavObj(SkillLevel skillLevel) {
        final EntityType skillLevelEntityType = metamodelService.getEntityTypeByEntTypeId(SkillLevel.getEntTypeId());
        EAVObject eavObj = new EAVObject(
                skillLevelEntityType,
                "Level of " + skillLevel.getSkill().getName()
        );
        eavObj.setId(skillLevel.getId());

        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(skillLevelEntityType.getId(), SkillLevel.getLevelId()),
                        skillLevel.getLevel()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(skillLevelEntityType.getId(), SkillLevel.getSkillRefId()),
                        eavService.getEAVObjById(skillLevel.getSkill().getId())
                )
        )));

        return eavObj;
    }

    public SkillLevel eavObjToSkillLevel(EAVObject skillLevelEavObj) {
        Skill skill = skillConverter.eavObjToDto(
                skillLevelEavObj
                        .getParameterByAttrId(SkillLevel.getSkillRefId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("This SkillLevel has no Skill object in it!")
                        )
                        .getReferenced()
        );

        return new SkillLevel(
                skillLevelEavObj.getId(),
                skillLevelEavObj
                        .getParameterByAttrId(SkillLevel.getLevelId())
                        .orElseGet(Parameter::new)
                        .getAttrValueInt(),
                skill
        );
    }

    public EAVObject dtoToEavObj(User user) {
        final EntityType userEntityType = metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId());
        EAVObject eavObj = new EAVObject(
                userEntityType,
                user.getUsername()
        );
        eavObj.setId(user.getId());

        eavObj.addParameters(new ArrayList<>(Arrays.asList(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getRoleId()),
                        Role.STAFF.ordinal()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getFirstNameId()),
                        user.getFirstName()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getLastNameId()),
                        user.getLastName()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getAgeId()),
                        user.getAge()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getEmailId()),
                        user.getEmail()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getAboutId()),
                        user.getAbout()
                ),
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getPositionId()),
                        (user.getPosition() != null) ? user.getPosition().ordinal() : Position.NEWCOMER.ordinal()
                )
        )));

        if (user.getDepartment() != null && user.getDepartment().getId() != null) {
            eavObj.addParameter(
                    new Parameter(
                            eavObj,
                            metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getDepartmentRefId()),
                            eavService.getEAVObjById(user.getDepartment().getId())
                    )
            );
        }
        if (user.getTeam() != null && user.getTeam().getId() != null) {
            eavObj.addParameter(
                    new Parameter(
                            eavObj,
                            metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getTeamRefId()),
                            eavService.getEAVObjById(user.getTeam().getId())
                    )
            );
        }

        Attribute skillLevelAttr = metamodelService.updateEntTypeAttrMapping(
                userEntityType.getId(),
                User.getSkillLevelRefId()
        );
        List<Parameter> skillLevelsAsParams = new ArrayList<>();
        for (SkillLevel skillLevel : user.getSkillLevels()) {
            skillLevelsAsParams.add(new Parameter(
                    eavObj,
                    skillLevelAttr,
                    this.skillLevelToEavObj(skillLevel)
            ));
        }
        eavObj.addParameters(skillLevelsAsParams);

        return eavObj;
    }

    public User eavObjToDto(EAVObject userEavObj) {
        User user = this.eavObjToDtoNoRefs(userEavObj);

        Optional<Parameter> departAsParam = userEavObj.getParameterByAttrId(User.getDepartmentRefId());
        Department department = new Department();
        if (departAsParam.isPresent()) {
            department = departmentConverter.eavObjToDtoNoRefs(departAsParam.get().getReferenced());
        }
        user.setDepartment(department);

        Optional<Parameter> teamAsParam = userEavObj.getParameterByAttrId(User.getTeamRefId());
        Team team = new Team();
        if (teamAsParam.isPresent()) {
            team = teamConverter.eavObjToDtoNoRefs(teamAsParam.get().getReferenced());
        }
        user.setTeam(team);

        return user;
    }

    public User eavObjToDtoNoRefs(EAVObject userEavObj) {
        Set<Role> roles = new HashSet<>();
        List<Parameter> rolesAsParams = userEavObj.getMultipleParametersByAttrId(User.getRoleId());
        for (Parameter roleParam : rolesAsParams) {
            roles.add(roleValues[Math.toIntExact(roleParam.getAttrValueInt())]);
        }

        List<Parameter> skillLevelsAsParams = userEavObj.getMultipleParametersByAttrId(User.getSkillLevelRefId());
        List<EAVObject> skillLevelsEavList = userEavObj
                .getMultipleParametersByAttrId(User.getSkillLevelRefId())
                .stream()
                .map(Parameter::getReferenced)
                .collect(Collectors.toList());

        Set<SkillLevel> skillLevels = new HashSet<>();
        for (EAVObject skillLevelEavObj : skillLevelsEavList) {
            Optional<Parameter> skillAsParam = skillLevelEavObj.getParameterByAttrId(SkillLevel.getSkillRefId());
            if (skillAsParam.isPresent()) {
                EAVObject skillEavObject = skillAsParam.get().getReferenced();
                skillLevels.add(new SkillLevel(
                                skillLevelEavObj.getId(),
                                skillLevelEavObj
                                        .getParameterByAttrId(SkillLevel.getLevelId())
                                        .map(Parameter::getAttrValueInt)
                                        .orElse(null),
                                skillConverter.eavObjToDto(skillEavObject)
                        )
                );
            }
        }

        return new User(
                userEavObj.getId(),
                userEavObj.getEntName(),
                null,
                roles,
                userEavObj.getParameterByAttrId(User.getFirstNameId())
                        .map(Parameter::getAttrValueTxt)
                        .orElse(""),
                userEavObj.getParameterByAttrId(User.getLastNameId())
                        .map(Parameter::getAttrValueTxt)
                        .orElse(""),
                userEavObj.getParameterByAttrId(User.getAgeId())
                        .map(Parameter::getAttrValueInt)
                        .orElse(null),
                userEavObj.getParameterByAttrId(User.getEmailId())
                        .map(Parameter::getAttrValueTxt)
                        .orElse(""),
                userEavObj.getParameterByAttrId(User.getAboutId())
                        .map(Parameter::getAttrValueTxt)
                        .orElse(""),
                positionValues[
                        userEavObj
                                .getParameterByAttrId(User.getPositionId())
                                .map(Parameter::getAttrValueInt)
                                .orElse(Position.NEWCOMER.ordinal())
                        ],
                skillLevels
        );
    }
}
