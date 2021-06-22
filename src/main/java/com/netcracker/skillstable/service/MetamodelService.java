package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.Attribute;
import com.netcracker.skillstable.model.EntityObj;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.repos.AttributeRepo;
import com.netcracker.skillstable.repos.EntTypeAttrRepo;
import com.netcracker.skillstable.repos.EntityTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetamodelService {
    private EntityTypeRepo entityTypeRepo;
    private AttributeRepo attributeRepo;
    private EntTypeAttrRepo entTypeAttrRepo;

    private EntityObjService entityObjService;

    public Optional<EntityType> getEntityTypeByEntId(Long entObjId) {
        Optional<EntityObj> optionalEntityObj = entityObjService.getEntityObjById(entObjId);
        if (!optionalEntityObj.isPresent()) {
            return Optional.empty();
        }

        EntityObj entObj = optionalEntityObj.get();
        return entityTypeRepo.findById(entObj.getEntTypeId());
    }

    public List<Attribute> getAttributesByEntTypeId(Long entTypeId) {
        System.out.println("Hello from the beginning of a service method!");
        List<Long> attrIds = entTypeAttrRepo.getAttrIdByEntTypeId(entTypeId);
        //System.out.println(attrIds);
        System.out.println("Hello from the end of a service method!");
        return attributeRepo.findAllById(attrIds);
    }
}
