package com.netcracker.skillstable.repo;

import com.netcracker.skillstable.model.eav.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParameterRepo extends JpaRepository<Parameter, Integer> {
}

