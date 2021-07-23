package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.SkillConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
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

    public Skill getSkillById(Integer skillId) {
        return skillConverter.eavObjToDto(eavService.getEAVObjById(skillId));
    }

    public Skill updateSkill(Skill skill, Integer skillId) {
        EAVObject dtoEavObj = skillConverter.dtoToEavObj(
                skill,
                metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId())
        );

        return skillConverter.eavObjToDto(eavService.updateEAVObj(dtoEavObj, skillId));
    }

    public void deleteSkill(Integer skillId) {
        Skill skill = this.getSkillById(skillId);

        for (User user : userService.getAllUsers()) {
            user.deleteSkillLevel(skill);
            userService.updateUser(user);
        }

        List<EAVObject> skillLevelEavList = eavService.getAllByEntTypeId(SkillLevel.getEntTypeId());
        for (EAVObject skillLevelEav : skillLevelEavList) {
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
