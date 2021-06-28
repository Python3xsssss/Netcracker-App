package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.*;
import com.netcracker.skillstable.repos.AttributeRepo;
import com.netcracker.skillstable.repos.EntTypeAttrRepo;
import com.netcracker.skillstable.repos.EntityTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MetamodelService {
    @Autowired
    private EntityTypeRepo entityTypeRepo;
    @Autowired
    private AttributeRepo attributeRepo;
    @Autowired
    private EntTypeAttrRepo entTypeAttrRepo;

    @Autowired
    private EAVService eavService;

    public EntityType getEntityTypeByEntId(Long entId) {
        Optional<EAVObject> optionalEAVObject = eavService.getEAVObjById(entId);
        return optionalEAVObject.map(EAVObject::getEntType).orElse(null);
    }

    public List<Attribute> getAttributesByEntTypeId(Long entTypeId) {
        List<EntTypeAttr> entTypeAttrList = entTypeAttrRepo.findByEntityTypeId(entTypeId);

        List<Attribute> attributes = new ArrayList<>();
        for (EntTypeAttr entTypeAttr : entTypeAttrList) {
            attributes.add(entTypeAttr.getAttribute());
        }

        return attributes;
    }

    public Attribute getAttributeByEntTypeIdAndAttrId(Long entTypeId, Long attrId) {
        return entTypeAttrRepo.findByEntityTypeIdAndAttributeId(entTypeId, attrId).getAttribute();
    }
}
