package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.EntityObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "entity", path = "entity")
public interface EntityObjRepo extends JpaRepository<EntityObj, Long> {
}

