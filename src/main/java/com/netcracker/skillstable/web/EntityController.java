package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.*;
import com.netcracker.skillstable.repos.ParameterRepo;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data")
public class EntityController {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private ParameterRepo parameterRepo;


    @GetMapping("/entities")
    public List<EAVObject> getAllEntities(){
        return eavService.getAll();
    }

    @GetMapping("/entities/{entId}")
    public Optional<EAVObject> getEntity(
            @PathVariable(value="entId") Integer entId
    ) {
        return eavService.getEAVObjById(entId);
    }

    @GetMapping("/entities/{entId}/parameters")
    public List<Parameter> getAllParametersByEAVObject(
            @PathVariable(value="entId") Integer entId
    ) {
        return parameterRepo.findByEavObjectId(entId);
    }

    @GetMapping("/entities/{entId}/parameters/{attrId}")
    public List<ParameterValue> getParameterByEAVObject(
            @PathVariable(value="entId") Integer entId,
            @PathVariable(value="attrId") Integer attrId
    ) {
        Optional<EAVObject> optEntity = eavService.getEAVObjById(entId);
        return optEntity.map(eavObject -> eavObject.getMultipleParametersByAttrId(attrId)).orElse(null);
    }

    @GetMapping("/entities/{entId}/attributes")
    public List<Attribute> getAllAttributesByEAVObject(
            @PathVariable(value="entId") Integer entId
    ) {
        EntityType entityType = metamodelService.getEntityTypeByEntId(entId);

        return (entityType != null) ? metamodelService.getAttributesByEntTypeId(entityType.getId()) : null;
    }

    @GetMapping("/entities/{entId}/attributes/{entAttrId}")
    public Attribute getAttributeByEAVObject(
            @PathVariable(value="entId") Integer entId,
            @PathVariable(value="entAttrId") Integer entAttrId
    ) {
        EntityType entityType = metamodelService.getEntityTypeByEntId(entId);

        return (entityType != null) ?
                metamodelService.getAttributeByEntTypeIdAndAttrId(entityType.getId(), entAttrId) :
                null;
    }
}
