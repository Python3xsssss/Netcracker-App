package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.EntTypeAttr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "entTypeAttr", path = "entTypeAttr")
public interface EntTypeAttrRepo extends JpaRepository<EntTypeAttr, Integer> {
    List<EntTypeAttr> findByEntityTypeId(Integer entTypeId);

    EntTypeAttr findByEntityTypeIdAndAttributeId(Integer entTypeId, Integer attrId);
}

