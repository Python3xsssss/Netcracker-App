package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "attribute", path = "attribute")
public interface AttributeRepo extends JpaRepository<Attribute, Integer> {
    Attribute findByName(String attrName);
}

