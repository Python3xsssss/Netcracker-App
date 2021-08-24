package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.exception.PasswordException;
import com.netcracker.skillstable.exception.ResourceAlreadyExistsException;
import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.model.eav.EntityType;
import com.netcracker.skillstable.model.eav.Parameter;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.enumeration.Role;
import com.netcracker.skillstable.service.eav.EAVService;
import com.netcracker.skillstable.service.eav.MetamodelService;
import com.netcracker.skillstable.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        EAVObject userEavObj;
        try {
            userEavObj = eavService.createEAVObj(userConverter.dtoToEavObj(user));
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("User '" + user.getUsername() + "' already exists!");
        }

        return userConverter.eavObjToDto(userEavObj);
    }

    public List<User> getAllUsers() {
        return eavService
                .getAllByEntTypeId(User.getEntTypeId())
                .stream()
                .map(userConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public User getUserById(Integer userId) {
        EAVObject userEavObj;
        try {
            userEavObj = eavService.getEAVObjById(userId);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("User not found!");
        }

        return userConverter.eavObjToDto(userEavObj);
    }

    public User getUserByUsername(String username) {
        EAVObject userEavObj;
        try {
            userEavObj = eavService.getEAVObjByNameAndType(
                    username,
                    metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
            );
        } catch (ResourceNotFoundException exception) {
            throw new UsernameNotFoundException("User '" + username + "' not found!");
        }

        return userConverter.eavObjToDto(userEavObj);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        EAVObject userEavObj;
        try {
            userEavObj = eavService.getEAVObjByNameAndType(
                    username,
                    metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
            );
        } catch (ResourceNotFoundException exception) {
            throw new UsernameNotFoundException("User '" + username + "' not found!");
        }

        User user = userConverter.eavObjToDto(userEavObj);

        String userEncPassword = userEavObj.getParameterByAttrId(User.getPasswordId())
                .map(Parameter::getAttrValueTxt)
                .orElseThrow(() -> new RuntimeException("User " + user.getUsername() + " has no password!"));

        if (passwordEncoder.matches(password, userEncPassword)) {
            return user;
        } else {
            throw new PasswordException();
        }
    }

    public User updateUser(User user) {
        EAVObject updatedUser;
        try {
            updatedUser = eavService.updateEAVObj(
                    userConverter.dtoToEavObj(user),
                    user.getId()
            );
        } catch (ResourceNotFoundException exception) {
            throw new UsernameNotFoundException("User '" + user.getUsername() + "' not found!");
        }

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
        User user;
        try {
            user = this.getUserById(userId);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("User not found!");
        }

        EAVObject skillLevelEavObj;
        try {
            skillLevelEavObj = eavService.createOrUpdateEAVObj(
                    userConverter.skillLevelToEavObj(skillLevel, user.getUsername())
            );
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(
                    "User '" + user.getUsername() +
                            "' doesn't have skill '" + skillLevel.getSkill().getName() + "'!"
            );
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException(
                    "User '" + user.getUsername() +
                            "' already has skill '" + skillLevel.getSkill().getName() + "'!"
            );
        }

        SkillLevel createdSkillLevel = userConverter.eavObjToSkillLevel(skillLevelEavObj);

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

        EAVObject userEavObj;
        try {
            userEavObj = eavService.updateEAVObj(eavObj, user.getId());
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("User '" + user.getUsername() + "' not found!");
        }

        return userConverter.eavObjToDto(userEavObj);
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

        try {
            eavService.updateEAVObj(eavObj, userId);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("User not found!");
        }
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

        try {
            eavService.createEAVObj(eavObject);
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("Creator already exists!");
        }
    }
}
