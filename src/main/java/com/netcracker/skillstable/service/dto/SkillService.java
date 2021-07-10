package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.SkillConverter;
import com.netcracker.skillstable.service.converter.TeamConverter;
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


    public Skill createSkill(Skill skill) {
        return SkillConverter.eavObjToDto(eavService.createEAVObj(
                SkillConverter.dtoToEavObj(
                        skill,
                        metamodelService.getEntityTypeByEntTypeId(Skill.getEntTypeId())
                )
        ));
    }

    public List<Skill> getAllSkills() {
        return eavService
                .getAllByEntTypeId(Skill.getEntTypeId())
                .stream()
                .map(SkillConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Optional<Skill> getSkillById(Integer skillId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(skillId);
        if (!optionalEavObj.isPresent() || !Skill.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject skillEavObj = optionalEavObj.get();

        return Optional.of(SkillConverter.eavObjToDto(skillEavObj));
    }

    public void deleteSkill(Integer skillId) {
        eavService.deleteEAVObj(skillId);
    }
}
