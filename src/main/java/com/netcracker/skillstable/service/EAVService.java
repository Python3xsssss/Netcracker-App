package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.Parameter;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.repos.EAVObjectRepo;
import com.netcracker.skillstable.repos.ParameterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class EAVService {
    @Autowired
    private EAVObjectRepo eavRepo;
    @Autowired
    private ParameterRepo parameterRepo;

    public EAVObject createEAVObj(EAVObject eavObj) {
        return eavRepo.save(eavObj);
    }

    public EAVObject creatOrUpdateEAVObj(EAVObject eavObj) {
        return (eavObj.getId() == null)
                ? this.createEAVObj(eavObj)
                : this.updateEAVObj(eavObj, eavObj.getId()).orElseGet(EAVObject::new);
    }

    public List<EAVObject> getAll() {
        return eavRepo.findAll();
    }

    public List<EAVObject> getAllByEntTypeId(Integer entTypeId) {
        return eavRepo.findAllByEntTypeId(entTypeId);
    }

    public Optional<EAVObject> getEAVObjById(Integer entId) {
        return eavRepo.findById(entId);
    }

    public Optional<EAVObject> updateEAVObj(EAVObject dtoEavObj, Integer eavObjId) {
        Optional<EAVObject> optionalEAVObject = eavRepo.findById(eavObjId);
        if (optionalEAVObject.isEmpty()) {
            return Optional.empty();
        }

        EAVObject databaseEavObj = optionalEAVObject.get();

        List<Parameter> newParameters = databaseEavObj.updateParameters(dtoEavObj.getParameters());

        parameterRepo.saveAll(newParameters);
        eavRepo.save(databaseEavObj);

        return eavRepo.findById(eavObjId);
    }

    public void deleteEAVObj(Integer entId) {
        eavRepo.deleteById(entId);
    }
}
