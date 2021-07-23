package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.*;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data")
public class EntityTestController {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;

    @GetMapping("/entities")
    public List<EAVObject> getAllEntities(){
        return eavService.getAll();
    }

    @GetMapping("/entities/{entId}")
    public EAVObject getEntity(
            @PathVariable(value="entId") Integer entId
    ) {
        return eavService.getEAVObjById(entId);
    }

    @GetMapping("/entities/{entId}/parameters/{attrId}")
    public List<ParameterValue> getParameterByEAVObject(
            @PathVariable(value="entId") Integer entId,
            @PathVariable(value="attrId") Integer attrId
    ) {
        return eavService.getEAVObjById(entId).getMultipleParametersByAttrId(attrId);
    }

    @GetMapping("/entities/{entId}/attributes")
    public List<Attribute> getAllAttributesByEAVObject(
            @PathVariable(value="entId") Integer entId
    ) {
        EntityType entityType = metamodelService.getEntityTypeByEntId(entId);

        return (entityType != null) ? metamodelService.getAttributesByEntTypeId(entityType.getId()) : null;
    }
}
