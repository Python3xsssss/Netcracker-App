package com.netcracker.skillstable.service;

import com.netcracker.skillstable.model.EntityObj;
import com.netcracker.skillstable.repos.EntityObjRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityObjService {
    @Autowired
    private EntityObjRepo entityObjRepo;

    public EntityObj createEntityObj(EntityObj entityObj) {
        return entityObjRepo.saveAndFlush(entityObj);
    }

    public List<EntityObj> getAll() {
        return entityObjRepo.findAll();
    }

    public Optional<EntityObj> getEntityObjById(Long entObjId) {
        return entityObjRepo.findById(entObjId);
    }

    public EntityObj updateEntityObj(EntityObj entityObj) {
        return entityObjRepo.saveAndFlush(entityObj);
    }

    public void deleteEntityObj(Long id) {
        entityObjRepo.deleteById(id);
    }
}
