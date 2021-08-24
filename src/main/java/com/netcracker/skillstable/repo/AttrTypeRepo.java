package com.netcracker.skillstable.repo;

import com.netcracker.skillstable.model.eav.AttrType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "attrType", path = "attrType")
public interface AttrTypeRepo extends JpaRepository<AttrType, Integer> {
}
