package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.OldEntityObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "entity", path = "entity")
public interface OldEntityObjRepo extends JpaRepository<OldEntityObj, Long> {
}

