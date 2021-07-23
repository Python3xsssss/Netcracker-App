package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.*;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.SkillConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private UserService userService;
    @Autowired
    private SkillConverter skillConverter;


    public Skill createSkill(Skill skill) {
        return skillConverter.eavObjToDto(eavService.createEAVObj(
                skillConverter.dtoToEavObj(
                        skill,
                        metamodelService.getEntityTypeByEntTypeId(Skill.getEntTypeId())
                )
        ));
    }

    public List<Skill> getAllSkills() {
        return eavService
                .getAllByEntTypeId(Skill.getEntTypeId())
                .stream()
                .map(skillConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Optional<Skill> getSkillById(Integer skillId) {
        EAVObject skillEavObj = eavService.getEAVObjById(skillId);;

        return Optional.of(skillConverter.eavObjToDto(skillEavObj));
    }

    public Skill updateSkill(Skill skill, Integer skillId) {
        EAVObject dtoEavObj = skillConverter.dtoToEavObj(
                skill,
                metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId())
        );

        return skillConverter.eavObjToDto(eavService.updateEAVObj(dtoEavObj, skillId));
    }

    public void deleteSkill(Integer skillId) {
        Optional<Skill> optionalSkill = this.getSkillById(skillId);
        if (optionalSkill.isEmpty()) {
            return;
        }

        Skill skill = optionalSkill.get();
        for (User user : userService.getAllUsers()) {
            user.deleteSkillLevel(skill);
            userService.updateUser(user);
        }

        for (EAVObject skillLevelEav: eavService.getAllByEntTypeId(SkillLevel.getEntTypeId())) {
            if (skillId.equals(
                    (
                            skillLevelEav
                                    .getParameterByAttrId(SkillLevel.getSkillRefId())
                                    .orElseGet(ParameterValue::new)
                    ).getValueInt()
            )) {
                eavService.deleteEAVObj(skillLevelEav.getId());
            }
        }
        
        eavService.deleteEAVObj(skillId);
    }
}
