package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "eavObjects", path = "eavObjects")
public interface EAVObjectRepo extends JpaRepository<EAVObject, Integer> {
    List<EAVObject> findAllByEntTypeId(Integer entTypeId);

    Optional<EAVObject> findAllByEntNameAndEntType(String name, EntityType entType);
}
