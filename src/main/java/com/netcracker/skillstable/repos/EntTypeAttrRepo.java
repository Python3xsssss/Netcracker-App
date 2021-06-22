package com.netcracker.skillstable.repos;

import com.netcracker.skillstable.model.EntTypeAttr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "entTypeAttr", path = "entTypeAttr")
public interface EntTypeAttrRepo extends JpaRepository<EntTypeAttr, Long> {
    @Query("SELECT eta.attrId FROM EntTypeAttr eta WHERE eta.entTypeId = :entTypeId")
    List<Long> getAttrIdByEntTypeId(@Param("entTypeId") Long entTypeId);
}

