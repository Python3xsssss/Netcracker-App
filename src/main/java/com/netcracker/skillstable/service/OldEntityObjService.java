package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.OldEntityObj;
import com.netcracker.skillstable.repos.OldEntityObjRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OldEntityObjService {
    @Autowired
    private OldEntityObjRepo entityObjRepo;

    public OldEntityObj createEntityObj(OldEntityObj entityObj) {
        return entityObjRepo.saveAndFlush(entityObj);
    }

    public List<OldEntityObj> getAll() {
        return entityObjRepo.findAll();
    }

    public Optional<OldEntityObj> getEntityObjById(Long entObjId) {
        return entityObjRepo.findById(entObjId);
    }

    public OldEntityObj updateEntityObj(OldEntityObj entityObj) {
        return entityObjRepo.saveAndFlush(entityObj);
    }

    public void deleteEntityObj(Long id) {
        entityObjRepo.deleteById(id);
    }
}
