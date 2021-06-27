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
    private ParameterRepo parameterRepo;
    @Autowired
    private EAVObjectRepo eavRepo;

    public EAVObject createEAVObj(EAVObject eavObj) {
        return eavRepo.save(eavObj);
    }

    public List<EAVObject> getAll() {
        return eavRepo.findAll();
    }

    /*public List<Parameter> getAllParametersById(Long entId) {
        return eavRepo.findAll();
    }*/

    public Optional<EAVObject> getEAVObjById(Long entId) {
        return eavRepo.findById(entId);
    }

    public void deleteEAVObj(Long entId) {
        eavRepo.deleteById(entId);
    }

    public void persist() {
        eavRepo.flush();
    }
}
