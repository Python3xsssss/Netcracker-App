package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
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

    /*public Optional<User> getUserById(Long userId) {
        Optional<EAVObject> userEavObj = eavService.getEAVObjById(userId);
        if (userEavObj.isPresent()) {
            Set<Role> roles = new HashSet<>();
            List<ParameterValue> rolesAsParams = userEavObj.get().getParametersByAttrId(User.getRoleId());
            for (ParameterValue roleParam : rolesAsParams) {
                roles.add(roleValues[Math.toIntExact(roleParam.getValueInt())]);
            }
            List<ParameterValue> userEavObj.get().getParametersByAttrId(User.getDepartmentRefId())
            EAVObject departEavObject = eavService.getEAVObjById();
            Department department = new Department(
                    eavService.getE
            );

            User user = new User(
                    userId,
                    userEavObj.get().getEntName(),
                    null,
                    roles,
                    userEavObj.get().getParametersByAttrId(User.getFirstNameId()),
                    userEavObj.get().getParametersByAttrId(User.getLastNameId()),
                    userEavObj.get().getParametersByAttrId(User.getAgeId()),
                    userEavObj.get().getParametersByAttrId(User.getEmailId()),
                    userEavObj.get().getParametersByAttrId(User.getAboutId()),
                    userEavObj.get().getParametersByAttrId(User.getFirstNameId()),
                    userEavObj.get().getParametersByAttrId(User.getFirstNameId()),
                    userEavObj.get().getParametersByAttrId(User.getFirstNameId()),
                    )
        }

    }*/

    public void deleteUser(Long userId) {
        // todo
    }
}
