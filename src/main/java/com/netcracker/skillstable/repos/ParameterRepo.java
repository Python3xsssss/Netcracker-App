package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "parameters", path = "parameters")
public interface ParameterRepo extends JpaRepository<Parameter, Integer> {
}

