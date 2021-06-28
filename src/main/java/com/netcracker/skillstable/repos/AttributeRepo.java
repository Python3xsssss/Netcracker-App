package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "attribute", path = "attribute")
public interface AttributeRepo extends JpaRepository<Attribute, Long> {
}

