package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.attr.Position;
import com.netcracker.skillstable.model.dto.attr.Role;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        return userConverter.eavObjToDto(eavService.createEAVObj(
                userConverter.dtoToEavObj(user)
        ));
    }

    public List<User> getAllUsers() {
        return eavService
                .getAllByEntTypeId(User.getEntTypeId())
                .stream()
                .map(userConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public User getUserById(Integer userId) {
        EAVObject userEavObj = eavService.getEAVObjById(userId);

        return userConverter.eavObjToDto(userEavObj);
    }

    public User getUserByUsername(String username) {
        EAVObject userEavObj = eavService.getEAVObjByNameAndType(
                username,
                metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
        );

        return userConverter.eavObjToDto(userEavObj);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        EAVObject userEavObj = eavService.getEAVObjByNameAndType(
                username,
                metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
        );

        User user = userConverter.eavObjToDto(userEavObj);

        String userEncPassword = userEavObj.getParameterByAttrId(User.getPasswordId())
                .map(Parameter::getAttrValueTxt)
                .orElseThrow(() -> new ResourceNotFoundException("User " + user.getUsername() + " has no password!"));
        if (passwordEncoder.matches(password, userEncPassword)) {
            return user;
        } else {
            throw new ResourceNotFoundException("Invalid password");
        }
    }

    public User updateUser(User user) {
        EAVObject updatedUser = eavService.updateEAVObj(
                userConverter.dtoToEavObj(user),
                user.getId()
        );

        return userConverter.eavObjToDto(updatedUser);
    }

    public void deleteUser(Integer userId) {
        EAVObject eavObj = eavService.getEAVObjById(userId);
        List<Parameter> skillLevelRefParams = eavObj.getMultipleParametersByAttrId(User.getSkillLevelRefId());
        for (Parameter refParam : skillLevelRefParams) {
            eavService.deleteEAVObj(refParam.getReferenced().getId());
        }

        eavService.deleteEAVObj(userId);
    }

    public SkillLevel createOrUpdateSkillLevel(Integer userId, SkillLevel skillLevel) {
        User user = this.getUserById(userId);

        SkillLevel createdSkillLevel = userConverter.eavObjToSkillLevel(
                eavService.createOrUpdateEAVObj(
                        userConverter.skillLevelToEavObj(skillLevel)
                )
        );

        if (skillLevel.getId() == null) {
            user.addSkillLevel(createdSkillLevel);
        }
        this.updateUser(user);

        return createdSkillLevel;
    }

    public void deleteSkillLevel(Integer userId, Integer skillLevelId) {
        eavService.deleteEAVObj(skillLevelId);
    }

    public User addRole(User user, String roleName) {
        EAVObject eavObj = userConverter.dtoToEavObj(user);
        final EntityType userEntityType = metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId());
        eavObj.addParameters(new ArrayList<>(Collections.singletonList(new Parameter(
                eavObj,
                metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getRoleId()),
                Role.valueOf(roleName).ordinal()
        ))));

        return userConverter.eavObjToDto(eavService.updateEAVObj(eavObj, user.getId()));
    }

    public void deleteRole(Integer userId, String roleName) {
        Role role = Role.valueOf(roleName);
        EAVObject eavObj = eavService.getEAVObjById(userId);
        List<Parameter> roleParams = eavObj.getMultipleParametersByAttrId(User.getRoleId());
        for (Parameter roleParam : roleParams) {
            if (User.getRoleId().equals(roleParam.getAttribute().getId()) && role.ordinal() == roleParam.getAttrValueInt()) {
                eavObj.deleteParameter(roleParam);
                break;
            }
        }

        eavService.updateEAVObj(eavObj, userId);
    }

    public void setCreator(User creator) {
        EAVObject eavObject = userConverter.dtoToEavObj(creator);
        final EntityType userEntityType = metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId());
        eavObject.deleteParameter(new Parameter(
                eavObject,
                metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getRoleId()),
                Role.USER.ordinal()
        ));
        eavObject.addParameter(new Parameter(
                eavObject,
                metamodelService.updateEntTypeAttrMapping(userEntityType.getId(), User.getRoleId()),
                Role.CREATOR.ordinal()
        ));

        eavService.createEAVObj(eavObject);
    }
}
