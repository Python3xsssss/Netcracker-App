package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.exception.ResourceAlreadyExistsException;
import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.model.eav.Parameter;
import com.netcracker.skillstable.service.converter.SkillConverter;
import com.netcracker.skillstable.service.eav.EAVService;
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
    private SkillConverter skillConverter;


    public Skill createSkill(Skill skill) {
        EAVObject skillEavObj;
        try {
            skillEavObj = eavService.createEAVObj(skillConverter.dtoToEavObj(skill));
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("Skill '" + skill.getName() + "' already exists!");
        }

        return skillConverter.eavObjToDto(skillEavObj);
    }

    public List<Skill> getAllSkills() {
        return eavService
                .getAllByEntTypeId(Skill.getEntTypeId())
                .stream()
                .map(skillConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Skill getSkillById(Integer skillId) {
        EAVObject skillEavObj;
        try {
            skillEavObj = eavService.getEAVObjById(skillId);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("Skill not found!");
        }

        return skillConverter.eavObjToDto(skillEavObj);
    }

    public Skill updateSkill(Skill skill) {
        EAVObject skillEavObj;
        try {
            skillEavObj = eavService.updateEAVObj(skillConverter.dtoToEavObj(skill), skill.getId());
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("Skill '" + skill.getName() + "' not found!");
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("Skill '" + skill.getName() + "' already exists!");
        }

        return skillConverter.eavObjToDto(skillEavObj);
    }

    public void deleteSkill(Integer skillId) {
        List<EAVObject> skillLevelEavList = eavService.getAllByEntTypeId(SkillLevel.getEntTypeId());
        for (EAVObject skillLevelEav : skillLevelEavList) {
            Integer skillIdToCheck = skillLevelEav
                    .getParameterByAttrId(SkillLevel.getSkillRefId())
                    .orElseGet(Parameter::new)
                    .getReferenced()
                    .getId();
            if (skillId.equals(skillIdToCheck)) {
                eavService.deleteEAVObj(skillLevelEav.getId());
            }
        }

        eavService.deleteEAVObj(skillId);
    }
}
