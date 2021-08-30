package com.netcracker.skillstable.repo;

import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.model.eav.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EAVObjectRepo extends JpaRepository<EAVObject, Integer> {
    List<EAVObject> findAllByEntTypeId(Integer entTypeId);
    Optional<EAVObject> findAllByEntNameAndEntType(String name, EntityType entType);
    Optional<EAVObject> findByEntNameAndEntType(String name, EntityType entType);
}
