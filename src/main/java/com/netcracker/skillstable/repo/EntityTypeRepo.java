package com.netcracker.skillstable.repo;


import com.netcracker.skillstable.model.eav.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntityTypeRepo extends JpaRepository<EntityType, Integer> {
}

