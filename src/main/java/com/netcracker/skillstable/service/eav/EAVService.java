package com.netcracker.skillstable.service.eav;

import com.netcracker.skillstable.exception.ResourceAlreadyExistsException;
import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.model.eav.EntityType;
import com.netcracker.skillstable.model.eav.Parameter;
import com.netcracker.skillstable.repo.EAVObjectRepo;
import com.netcracker.skillstable.repo.ParameterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EAVService {
    @Autowired
    private EAVObjectRepo eavRepo;
    @Autowired
    private ParameterRepo parameterRepo;
    @Autowired
    private MetamodelService metamodelService;

    private static final Integer refTypeId = 3;

    public EAVObject createEAVObj(EAVObject eavObj) throws ResourceAlreadyExistsException {
        if (eavRepo.findByEntNameAndEntType(eavObj.getEntName(), eavObj.getEntType()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "EAVObject with name: " + eavObj.getEntName() +
                            " and type: " + eavObj.getEntType().getName() +
                            " already exists!"
            );
        }
        return eavRepo.save(eavObj);
    }

    public EAVObject createOrUpdateEAVObj(EAVObject eavObj)
            throws ResourceNotFoundException, ResourceAlreadyExistsException {
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

    public EAVObject getEAVObjById(Integer eavObjId) throws ResourceNotFoundException {
        return eavRepo.findById(eavObjId).orElseThrow(
                () -> new ResourceNotFoundException("EAVObject with id=" + eavObjId + " not found!")
        );
    }

    public EAVObject getEAVObjByNameAndType(String name, EntityType entityType) throws ResourceNotFoundException {
        return eavRepo.findAllByEntNameAndEntType(name, entityType).orElseThrow(
                () -> new ResourceNotFoundException("EAVObject with name=" + name + " not found!")
        );
    }

    public EAVObject updateEAVObj(EAVObject dtoEavObj, Integer eavObjId) throws ResourceNotFoundException {
        EAVObject databaseEavObj = eavRepo.findById(eavObjId).orElseThrow(
                () -> new ResourceNotFoundException("EAVObject with id=" + eavObjId + " not found!")
        );

        databaseEavObj.setEntName(dtoEavObj.getEntName());
        List<Parameter> newParameters = databaseEavObj.updateParameters(dtoEavObj.getParameters());
        parameterRepo.saveAll(newParameters);
        EAVObject savedEavObj = eavRepo.save(databaseEavObj);
        savedEavObj.addParameters(newParameters);
        return savedEavObj;
    }

    public void deleteEAVObj(Integer eavObjId) {
        eavRepo.deleteById(eavObjId);
    }
}
