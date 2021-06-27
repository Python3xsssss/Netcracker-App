package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.EAVObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "eavObjects", path = "eavObjects")
public interface EAVObjectRepo extends JpaRepository<EAVObject, Long> {
}
