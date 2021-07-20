package com.netcracker.skillstable.service.dto;

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


    public User createUser(User user) {
        return userConverter.eavObjToDto(eavService.createEAVObj(
                userConverter.dtoToEavObj(
                        user,
                        metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
                )
        ));
    }

    public List<User> getAllUsers() {
        return eavService
                .getAllByEntTypeId(User.getEntTypeId())
                .stream()
                .map(userConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(Integer userId) {

        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(userId);
        if (optionalEavObj.isEmpty() || !User.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject userEavObj = optionalEavObj.get();

        return Optional.of(userConverter.eavObjToDto(userEavObj));
    }

    public Optional<User> updateUser(User user, Integer userId) {
        Optional<EAVObject> optionalUserFromRepo = eavService.getEAVObjById(userId);
        if (optionalUserFromRepo.isEmpty()) {
            return Optional.empty();
        }

        Set<SkillLevel> updatedSkillLevels = new HashSet<>();
        for (SkillLevel skillLevel : user.getSkillLevels()) {
            EAVObject skillLevelAsEavObj = eavService.creatOrUpdateEAVObj(userConverter.skillLevelToEavObj(
                    skillLevel,
                    metamodelService.getEntityTypeByEntTypeId(SkillLevel.getEntTypeId())
            ));
            skillLevel.setId(skillLevelAsEavObj.getId());
            updatedSkillLevels.add(skillLevel);
        }

        EAVObject userFromRepo = optionalUserFromRepo.get();
        List<Integer> refsFromRepo = userFromRepo
                .getMultipleParametersByAttrId(User.getSkillLevelRefId())
                .stream()
                .map(ParameterValue::getValueInt)
                .collect(Collectors.toList());

        List<Integer> refsFromDto = updatedSkillLevels.stream().map(SkillLevel::getId).collect(Collectors.toList());
        for (Integer ref : refsFromRepo) {
            if (!refsFromDto.contains(ref)) {
                eavService.deleteEAVObj(ref);
            }
        }

        user.setSkillLevels(updatedSkillLevels);

        eavService.updateEAVObj(
                userConverter.dtoToEavObj(
                        user,
                        metamodelService.getEntityTypeByEntTypeId(User.getEntTypeId())
                ),
                userId
        );
        System.out.println("\n\n\n00000000\n\n" + this.getUserById(userId).orElseGet(User::new) + "\n\n\n00000000\n\n");
        return this.getUserById(userId);
    }

    public void deleteUser(Integer userId) {
        Optional<EAVObject> optEavObj = eavService.getEAVObjById(userId);
        if (optEavObj.isEmpty()) {
            return;
        }

        EAVObject eavObj = optEavObj.get();
        List<ParameterValue> skillLevelRefs = eavObj.getMultipleParametersByAttrId(User.getSkillLevelRefId());
        for (ParameterValue ref : skillLevelRefs) {
            eavService.deleteEAVObj(ref.getValueInt());
        }

        eavService.deleteEAVObj(userId);
    }
}
