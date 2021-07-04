package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.*;
import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;

    private final Role[] roleValues = Role.values();
    private final Position[] positionValues = Position.values();

    public Integer createUser(User user) {
        EAVObject eavObj = new EAVObject(
                metamodelService.getEntityTypeByEntId(User.getEntTypeId()),
                user.getUsername()
        );

        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(eavObj, User.getRoleId(), Role.STAFF.ordinal()),
                new Parameter(eavObj, User.getFirstNameId(), user.getFirstName()),
                new Parameter(eavObj, User.getLastNameId(), user.getLastName()),
                new Parameter(eavObj, User.getAgeId(), user.getAge()),
                new Parameter(eavObj, User.getEmailId(), user.getEmail()),
                new Parameter(eavObj, User.getAboutId(), user.getAbout()),
                new Parameter(eavObj, User.getDepartmentRefId(), user.getDepartment().getId()),
                new Parameter(eavObj, User.getTeamRefId(), user.getTeam().getId()),
                new Parameter(eavObj, User.getPositionId(), user.getPosition().ordinal())
        )));

        List<Parameter> skillsAsParams = new ArrayList<>();
        for (Skill skill : user.getSkills()) {
            skillsAsParams.add(new Parameter(eavObj, User.getSkillRefId(), skill.getId()));
        }
        eavObj.addParameters(skillsAsParams);

        return eavService.createEAVObj(eavObj).getId();
    }

    public List<User> getAllUsers() {
        return null; // todo
    }

    public Optional<User> getUserById(Integer userId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(userId);
        if (!optionalEavObj.isPresent() || !User.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject userEavObj = optionalEavObj.get();

        Set<Role> roles = new HashSet<>();
        List<ParameterValue> rolesAsParams = userEavObj.getMultipleParametersByAttrId(User.getRoleId());
        for (ParameterValue roleParam : rolesAsParams) {
            roles.add(roleValues[Math.toIntExact(roleParam.getValueInt())]);
        }

        Optional<ParameterValue> departAsParam = userEavObj.getParameterByAttrId(User.getDepartmentRefId());
        Department department = new Department();
        if (departAsParam.isPresent()) {
            Optional<EAVObject> departEavObject = eavService.getEAVObjById(departAsParam.get().getValueInt());
            department = departEavObject.map(eavObject -> new Department(
                    eavObject.getId(),
                    eavObject.getEntName()
            )).orElseGet(Department::new);
        }


        Optional<ParameterValue> teamAsParam = userEavObj.getParameterByAttrId(User.getTeamRefId());
        Team team = new Team();
        if (teamAsParam.isPresent()) {
            Optional<EAVObject> teamEavObject = eavService.getEAVObjById(teamAsParam.get().getValueInt());
            team = teamEavObject.map(eavObject -> new Team(
                    eavObject.getId(),
                    eavObject.getEntName()
            )).orElseGet(Team::new);
        }

        List<ParameterValue> skillsAsParams = userEavObj.getMultipleParametersByAttrId(User.getSkillRefId());
        List<EAVObject> skillsEavList = new ArrayList<>();
        for (ParameterValue skillAsParam : skillsAsParams) {
            Optional<EAVObject> optSkillEavObj = eavService.getEAVObjById(skillAsParam.getValueInt());
            optSkillEavObj.ifPresent(skillsEavList::add);
        }
        Set<Skill> skills = new HashSet<>();
        for (EAVObject skillAsEavObj : skillsEavList) {
            skills.add(new Skill(
                    skillAsEavObj.getId(),
                    skillAsEavObj.getEntName(),
                    skillAsEavObj.getMultipleParametersByAttrId(Skill.getAboutId()).get(0).getValueStr(),
                    Math.toIntExact(skillAsEavObj.getMultipleParametersByAttrId(Skill.getLevelId()).get(0).getValueInt())
            ));
        }

        return Optional.of(new User(
                userId,
                userEavObj.getEntName(),
                null,
                roles,
                userEavObj.getParameterByAttrId(User.getFirstNameId()).map(ParameterValue::getValueStr).orElse(null),
                userEavObj.getParameterByAttrId(User.getLastNameId()).map(ParameterValue::getValueStr).orElse(null),
                userEavObj.getParameterByAttrId(User.getAgeId()).map(ParameterValue::getValueInt).orElse(null),
                userEavObj.getParameterByAttrId(User.getEmailId()).map(ParameterValue::getValueStr).orElse(null),
                userEavObj.getParameterByAttrId(User.getAboutId()).map(ParameterValue::getValueStr).orElse(null),
                department,
                team,
                positionValues[
                        userEavObj.getParameterByAttrId(User.getPositionId())
                                .map(ParameterValue::getValueInt)
                                .orElse(Position.NEWCOMER.ordinal())
                        ],
                skills
                ));
    }

    public void deleteUser(Integer userId) {
        // todo
    }
}
