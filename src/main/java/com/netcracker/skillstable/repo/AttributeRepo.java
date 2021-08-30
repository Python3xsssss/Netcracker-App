package com.netcracker.skillstable.repo;

import com.netcracker.skillstable.model.eav.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AttributeRepo extends JpaRepository<Attribute, Integer> {
    Attribute findByName(String attrName);
}

