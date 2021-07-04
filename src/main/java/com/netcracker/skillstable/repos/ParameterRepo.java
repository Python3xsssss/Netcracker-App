package com.netcracker.skillstable.repos;

import java.util.List;
import java.util.Optional;

import com.netcracker.skillstable.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "parameters", path = "parameters")
public interface ParameterRepo extends JpaRepository<Parameter, Integer> {
    List<Parameter> findByEavObjectId(Integer eavId);
    Optional<Parameter> findByIdAndEavObjectId(Integer id, Integer eavId);
}

