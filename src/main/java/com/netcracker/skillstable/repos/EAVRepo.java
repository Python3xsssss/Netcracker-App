package com.netcracker.skillstable.repos;

import java.util.List;

import com.netcracker.skillstable.model.EAVObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "eav", path = "eav")
public interface EAVRepo extends JpaRepository<EAVObject, Long> {
    List<EAVObject> getByEntIdAndAttrId(@Param("entId") Long entId, @Param("attrId") Long attrId);
    void deleteByEntIdAndAttrId(@Param("entId") Long entId, @Param("attrId") Long attrId);
    List<EAVObject> findAllByEntId(@Param("entId") Long entId);
}

