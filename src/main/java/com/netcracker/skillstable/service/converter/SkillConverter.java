package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillConverter {
    @Autowired
    private MetamodelService metamodelService;

    public EAVObject dtoToEavObj(Skill skill, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                skill.getName()
        );
        eavObj.setId(skill.getId());

        eavObj.addParameter(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(entityType.getId(), Skill.getAboutId()),
                        skill.getAbout()
                )
        );

        return eavObj;
    }

    public Skill eavObjToDto(EAVObject skillEavObj) {
        return new Skill(
                skillEavObj.getId(),
                skillEavObj.getEntName(),
                skillEavObj
                        .getParameterByAttrId(Skill.getAboutId())
                        .map(ParameterValue::getValueTxt)
                        .orElse(null)
        );
    }
}
