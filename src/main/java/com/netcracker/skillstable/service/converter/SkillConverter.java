package com.netcracker.skillstable.service.converter;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.service.EAVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class SkillConverter {
    @Autowired
    private static EAVService eavService;

    public static EAVObject dtoToEavObj(Skill skill, EntityType entityType) {
        EAVObject eavObj = new EAVObject(
                entityType,
                skill.getName()
        );

        eavObj.addParameters(new ArrayList<Parameter>(Collections.singletonList(
                new Parameter(eavObj, Skill.getAboutId(), skill.getAbout())
        )));

        return eavObj;
    }

    public static Skill eavObjToDto(EAVObject skillEavObj) {
        return Skill.builder()
                .id(skillEavObj.getId())
                .name(skillEavObj.getEntName())
                .about(skillEavObj.getParameterByAttrId(Skill.getAboutId()).map(ParameterValue::getValueStr).orElse(null))
                .build();
    }
}
