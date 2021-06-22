package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.repos.EAVRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EAVService {
    @Autowired
    private EAVRepo eavRepo;

    public EAVObject createEAVObj(EAVObject eavObj) {
        return eavRepo.saveAndFlush(eavObj);
    }

    public List<EAVObject> getAll() {
        return eavRepo.findAll();
    }

    public List<EAVObject> getAllByEntId(Long entId) {
        return eavRepo.findAllByEntId(entId);
    }

    public List<EAVObject> getEAVObjById(Long entObjId, Long attrId) {
        return eavRepo.getByEntIdAndAttrId(entObjId, attrId);
    }

    public EAVObject updateEAVObj(EAVObject eavObj) {
        return eavRepo.saveAndFlush(eavObj);
    }

    public void deleteEAVObj(Long entObjId, Long attrId) {
        eavRepo.deleteByEntIdAndAttrId(entObjId, attrId);
    }
}
