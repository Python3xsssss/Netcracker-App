package com.netcracker.skillstable.repo;

import com.netcracker.skillstable.model.eav.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "parameters", path = "parameters")
public interface ParameterRepo extends JpaRepository<Parameter, Integer> {
}

