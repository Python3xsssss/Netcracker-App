package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.SkillConverter;
import com.netcracker.skillstable.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private SkillConverter skillConverter;


    @Transactional
    public User createUser(User user) {
        return userConverter.eavObjToDto(eavService.createEAVObj(
                userConverter.dtoToEavObj(
                        user,
                        metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
                )
        ));
    }

    @Transactional
    public List<User> getAllUsers() {
        return eavService
                .getAllByEntTypeId(User.getEntTypeId())
                .stream()
                .map(userConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public User getUserById(Integer userId) {
        EAVObject userEavObj = eavService.getEAVObjById(userId);

        return userConverter.eavObjToDto(userEavObj);
    }

    @Transactional
    public User updateUser(User user) {
        EAVObject updatedUser = eavService.updateEAVObj(
                userConverter.dtoToEavObj(
                        user,
                        metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
                ),
                user.getId()
        );

        return userConverter.eavObjToDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Integer userId) {
        EAVObject eavObj = eavService.getEAVObjById(userId);
        List<ParameterValue> skillLevelRefs = eavObj.getMultipleParametersByAttrId(User.getSkillLevelRefId());
        for (ParameterValue ref : skillLevelRefs) {
            eavService.deleteEAVObj(ref.getValueInt());
        }

        eavService.deleteEAVObj(userId);
    }

    @Transactional
    public SkillLevel createOrUpdateSkillLevel(Integer userId, SkillLevel skillLevel) {
        User user = this.getUserById(userId);

        SkillLevel createdSkillLevel = userConverter.eavObjToSkillLevel(
                eavService.createOrUpdateEAVObj(
                        userConverter.skillLevelToEavObj(
                                skillLevel,
                                metamodelService.getEntityTypeByEntTypeId(SkillLevel.getEntTypeId())
                        )
                )
        );

        if (skillLevel.getId() == null) {
            user.addSkillLevel(createdSkillLevel);
        }
        this.updateUser(user);

        return createdSkillLevel;
    }

    @Transactional
    public void deleteSkillLevel(Integer userId, Integer skillLevelId) {
        User user = this.getUserById(userId);

        eavService.deleteEAVObj(skillLevelId);

        user.deleteSkillLevel(skillLevelId);
        this.updateUser(user);
    }
}
