package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
