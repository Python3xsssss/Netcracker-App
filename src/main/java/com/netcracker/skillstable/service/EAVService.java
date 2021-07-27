package com.netcracker.skillstable.service;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.repos.EAVObjectRepo;
import com.netcracker.skillstable.repos.ParameterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EAVService {
    @Autowired
    private EAVObjectRepo eavRepo;
    @Autowired
    private ParameterRepo parameterRepo;
    @Autowired
    private MetamodelService metamodelService;

    private static final Integer refTypeId = 3;

    public EAVObject createEAVObj(EAVObject eavObj) {
        return eavRepo.save(eavObj);
    }

    public EAVObject createOrUpdateEAVObj(EAVObject eavObj) {
        return (eavObj.getId() == null)
                ? this.createEAVObj(eavObj)
                : this.updateEAVObj(eavObj, eavObj.getId());
    }

    public List<EAVObject> getAll() {
        return eavRepo.findAll();
    }

    public List<EAVObject> getAllByEntTypeId(Integer entTypeId) {
        return eavRepo.findAllByEntTypeId(entTypeId);
    }

    public EAVObject getEAVObjById(Integer eavObjId) {
        return eavRepo.findById(eavObjId).orElseThrow(
                () -> new ResourceNotFoundException("EAVObject with id=" + eavObjId + " not found!")
        );
    }

    public EAVObject getEAVObjByNameAndType(String name, EntityType entityType) {
        return eavRepo.findAllByEntNameAndEntType(name, entityType);
    }

    public EAVObject updateEAVObj(EAVObject dtoEavObj, Integer eavObjId) {
        EAVObject databaseEavObj = eavRepo.findById(eavObjId).orElseThrow(
                () -> new ResourceNotFoundException("EAVObject with id=" + eavObjId + " not found!")
        );

        databaseEavObj.setEntName(dtoEavObj.getEntName());
        List<Parameter> newParameters = databaseEavObj.updateParameters(dtoEavObj.getParameters());
        parameterRepo.saveAll(newParameters);

        return eavRepo.save(databaseEavObj);
    }

    public void deleteEAVObj(Integer entId) {
        eavRepo.deleteById(entId);
    }
}
