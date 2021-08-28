package com.netcracker.skillstable.service.eav;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.eav.AttrType;
import com.netcracker.skillstable.model.eav.Attribute;
import com.netcracker.skillstable.model.eav.EntTypeAttr;
import com.netcracker.skillstable.model.eav.EntityType;
import com.netcracker.skillstable.repo.AttrTypeRepo;
import com.netcracker.skillstable.repo.AttributeRepo;
import com.netcracker.skillstable.repo.EntTypeAttrRepo;
import com.netcracker.skillstable.repo.EntityTypeRepo;
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
    private AttrTypeRepo attrTypeRepo;
    @Autowired
    private EntTypeAttrRepo entTypeAttrRepo;

    @Autowired
    private EAVService eavService;

    public EntityType getEntityTypeByEntTypeId(Integer id) {
        return entityTypeRepo.getById(id);
    }

    public EntityType getEntityTypeByEntId(Integer entId) {
        return eavService.getEAVObjById(entId).getEntType();
    }

    public AttrType getAttrTypeByAttrTypeId(Integer attrTypeId) throws ResourceNotFoundException {
        return attrTypeRepo.findById(attrTypeId).orElseThrow(
                () -> new ResourceNotFoundException("The attribute type with id=" + attrTypeId + " not found!")
        );
    }

    public Attribute getAttributeByAttrName(String attrName) {
        return attributeRepo.findByName(attrName);
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

    public void setEntTypeAttrMapping(Integer entTypeId, Integer attrId) {
        EntityType entType = entityTypeRepo.getById(entTypeId);
        Attribute attr = attributeRepo.getById(attrId);
        entTypeAttrRepo.save(new EntTypeAttr(entType, attr));
    }

    public Attribute updateEntTypeAttrMapping(Integer entTypeId, Integer attrId) {
        Integer parentId = this.getEntityTypeByEntTypeId(entTypeId).getEntParentId();
        if (parentId != null) {
            Optional<Attribute> optionalAttribute = this.getEntTypeAttrMapping(parentId, attrId);
            if (optionalAttribute.isPresent()) {
                return optionalAttribute.get();
            }
        }

        if (this.getEntTypeAttrMapping(entTypeId, attrId).isEmpty()) {
            this.setEntTypeAttrMapping(entTypeId, attrId);
        }
        return this.getEntTypeAttrMapping(entTypeId, attrId).get();
    }
}
