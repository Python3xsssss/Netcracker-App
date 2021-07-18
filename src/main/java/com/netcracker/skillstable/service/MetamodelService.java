package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.Attribute;
import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntTypeAttr;
import com.netcracker.skillstable.model.EntityType;
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

    public EntityType getEntityTypeByEntTypeId(Integer id) {
        return entityTypeRepo.getById(id);
    }

    public EntityType getEntityTypeByEntId(Integer entId) {
        Optional<EAVObject> optionalEAVObject = eavService.getEAVObjById(entId);
        return optionalEAVObject.map(EAVObject::getEntType).orElse(null);
    }

    public List<Attribute> getAttributesByEntTypeId(Integer entTypeId) {
        List<EntTypeAttr> entTypeAttrList = entTypeAttrRepo.findByEntityTypeId(entTypeId);

        List<Attribute> attributes = new ArrayList<>();
        for (EntTypeAttr entTypeAttr : entTypeAttrList) {
            attributes.add(entTypeAttr.getAttribute());
        }

        return attributes;
    }

    public Optional<Attribute> getEntTypeAttrMapping(Integer entTypeId, Integer attrId) {
        Optional<EntTypeAttr> optionalTypeAttr = Optional.ofNullable(
                entTypeAttrRepo.findByEntityTypeIdAndAttributeId(entTypeId, attrId)
        );
        if (optionalTypeAttr.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(optionalTypeAttr.get().getAttribute());
    }

    public Attribute getAttributeByAttrName(String attrName) {
        return attributeRepo.findByName(attrName);
    }

    public void setEntTypeAttrMapping(Integer entTypeId, Integer attrId) {
        EntityType entType = entityTypeRepo.getById(entTypeId);
        Attribute attr = attributeRepo.getById(attrId);
        entTypeAttrRepo.save(new EntTypeAttr(entType, attr));
    }

    public Attribute updateEntTypeAttrMapping(Integer entTypeId, Integer attrId) {
        if (this.getEntTypeAttrMapping(entTypeId, attrId).isEmpty()) {
            this.setEntTypeAttrMapping(entTypeId, attrId);
        }
        return this.getEntTypeAttrMapping(entTypeId, attrId).get();
    }
}
