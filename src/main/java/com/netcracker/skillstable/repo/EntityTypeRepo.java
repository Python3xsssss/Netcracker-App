package com.netcracker.skillstable.repo;


import com.netcracker.skillstable.model.eav.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "entityType", path = "entityType")
public interface EntityTypeRepo extends JpaRepository<EntityType, Integer> {
}

