package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.ParameterValue;
import com.netcracker.skillstable.repos.ParameterRepo;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.OldEntityObjService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data")
public class HelloController {
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
            @PathVariable(value="entId") Long entId
    ) {
        return eavService.getEAVObjById(entId);
    }

    @GetMapping("/entities/{entId}/parameters")
    public List<Parameter> getParametersByEAVObject(
            @PathVariable(value="entId") Long entId
    ) {
        return parameterRepo.findByEavObjectId(entId);
    }

    @GetMapping("/entities/{entId}/parameters/{attrId}")
    public List<ParameterValue> testGetParametersByEAVObject(
            @PathVariable(value="entId") Long entId,
            @PathVariable(value="attrId") Long attrId
    ) {
        Optional<EAVObject> optEntity = eavService.getEAVObjById(entId);
        return optEntity.map(eavObject -> eavObject.getParameterByAttrId(attrId)).orElse(null);
    }
}
