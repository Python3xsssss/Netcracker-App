package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.Attribute;
import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityObj;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.EntityObjService;
import com.netcracker.skillstable.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class HelloController {
    @Autowired
    private EAVService eavService;
    @Autowired
    private EntityObjService entityObjService;
    @Autowired
    private MetamodelService metamodelService;


    @GetMapping("/entities")
    public List<EntityObj> getEntities(){
        return entityObjService.getAll();
    }

    @GetMapping("/eavObjects")
    public List<EAVObject> getEavObjects(){
        return eavService.getAll();
    }

    @GetMapping("/metadata")
    public List<Attribute> getUserAttributes(){
        System.out.println("Hello from controller method!");
        return  metamodelService.getAttributesByEntTypeId(1L);
    }
}
