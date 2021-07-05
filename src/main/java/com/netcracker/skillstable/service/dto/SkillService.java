package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Skill;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SkillService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;


    public Integer createSkill(Skill skill) {
        EAVObject eavObj = new EAVObject(
                metamodelService.getEntityTypeByEntTypeId(Skill.getEntTypeId()),
                skill.getName()
        );

        eavObj.addParameters(new ArrayList<Parameter>(Arrays.asList(
                new Parameter(eavObj, Skill.getAboutId(), skill.getAbout()),
                new Parameter(eavObj, Skill.getLevelId(), skill.getLevel()) // I think I'm missing something about skills logic...
        )));

        return eavService.createEAVObj(eavObj).getId();
    }
}
