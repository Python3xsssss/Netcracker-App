package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.EAVObject;
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

    public EAVObject createEAVObj(EAVObject eavObj) {
        return eavRepo.save(eavObj);
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

    public EAVObject updateEAVObj(EAVObject eavObj, List<Parameter> parameters) {
        /*List<Parameter> paramsFromRepo = parameterRepo.findByEavObjectId(eavObj.getId());
        for (Parameter parameter : parameters) {
            if (paramsFromRepo.contains())
        }
        eavObj.setParameters(paramsFromRepo);*/
        return eavRepo.save(eavObj);
    }

    public void deleteEAVObj(Integer entId) {
        eavRepo.deleteById(entId);
    }
}
