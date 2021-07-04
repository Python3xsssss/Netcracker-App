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

    public Long createUser(User user) {
        EAVObject eavObj = new EAVObject(
                metamodelService.getEntityTypeByEntId(User.getEntTypeId()),
                user.getUsername()
        );

        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(eavObj, User.getRoleId(), (long) Role.STAFF.ordinal()),
                new Parameter(eavObj, User.getFirstNameId(), user.getFirstName()),
                new Parameter(eavObj, User.getLastNameId(), user.getLastName()),
                new Parameter(eavObj, User.getAgeId(), (long) user.getAge()),
                new Parameter(eavObj, User.getEmailId(), user.getEmail()),
                new Parameter(eavObj, User.getAboutId(), user.getAbout()),
                new Parameter(eavObj, User.getDepartmentRefId(), user.getDepartment().getId()),
                new Parameter(eavObj, User.getTeamRefId(), user.getTeam().getId()),
                new Parameter(eavObj, User.getPositionId(), (long) user.getPosition().ordinal())
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

    public Optional<User> getUserById(Long userId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(userId);
        if (!optionalEavObj.isPresent()) {
            return Optional.empty();
        }

        EAVObject userEavObj = optionalEavObj.get();

        Set<Role> roles = new HashSet<>();
        List<ParameterValue> rolesAsParams = userEavObj.getParametersByAttrId(User.getRoleId());
        for (ParameterValue roleParam : rolesAsParams) {
            roles.add(roleValues[Math.toIntExact(roleParam.getValueInt())]);
        }

        List<ParameterValue> departsAsParams = userEavObj.getParametersByAttrId(User.getDepartmentRefId());
        Optional<EAVObject> departEavObject = eavService.getEAVObjById(departsAsParams.get(0).getValueInt());
        Department department = departEavObject.map(eavObject -> new Department(
                eavObject.getId(),
                eavObject.getEntName()
        )).orElseGet(Department::new);

        List<ParameterValue> teamsAsParams = userEavObj.getParametersByAttrId(User.getTeamRefId());
        Optional<EAVObject> teamEavObject = eavService.getEAVObjById(teamsAsParams.get(0).getValueInt());
        Team team = teamEavObject.map(eavObject -> new Team(
                eavObject.getId(),
                eavObject.getEntName()
        )).orElseGet(Team::new);

        List<ParameterValue> skillsAsParams = userEavObj.getParametersByAttrId(User.getSkillRefId());
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
                    skillAsEavObj.getParametersByAttrId(Skill.getAboutId()).get(0).getValueStr(),
                    Math.toIntExact(skillAsEavObj.getParametersByAttrId(Skill.getLevelId()).get(0).getValueInt())
            ));
        }

        User user = new User(
                userId,
                userEavObj.getEntName(),
                null,
                roles,
                userEavObj.getParametersByAttrId(User.getFirstNameId()).get(0).getValueStr(),
                userEavObj.getParametersByAttrId(User.getLastNameId()).get(0).getValueStr(),
                Math.toIntExact(userEavObj.getParametersByAttrId(User.getAgeId()).get(0).getValueInt()),
                userEavObj.getParametersByAttrId(User.getEmailId()).get(0).getValueStr(),
                userEavObj.getParametersByAttrId(User.getAboutId()).get(0).getValueStr(),
                department,
                team,
                positionValues[
                        Math.toIntExact(
                                userEavObj
                                        .getParametersByAttrId(User.getPositionId())
                                        .get(0)
                                        .getValueInt()
                                )
                        ],
                skills
                );

        return Optional.of(user);
    }

    public void deleteUser(Long userId) {
        // todo
    }
}
