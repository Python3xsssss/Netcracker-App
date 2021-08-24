package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.model.eav.EntityType;
import com.netcracker.skillstable.model.eav.Parameter;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.service.eav.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillConverter {
    @Autowired
    private MetamodelService metamodelService;


    public EAVObject dtoToEavObj(Skill skill) {
        final EntityType skillEntityType = metamodelService.getEntityTypeByEntTypeId(Skill.getEntTypeId());
        EAVObject eavObj = new EAVObject(
                skillEntityType,
                skill.getName()
        );
        eavObj.setId(skill.getId());

        eavObj.addParameter(
                new Parameter(
                        eavObj,
                        metamodelService.updateEntTypeAttrMapping(skillEntityType.getId(), Skill.getAboutId()),
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
                        .map(Parameter::getAttrValueTxt)
                        .orElse("")
        );
    }
}
