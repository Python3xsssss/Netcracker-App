package com.netcracker.skillstable.repo;

import com.netcracker.skillstable.model.eav.EntTypeAttr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EntTypeAttrRepo extends JpaRepository<EntTypeAttr, Integer> {
    List<EntTypeAttr> findByEntityTypeId(Integer entTypeId);

    EntTypeAttr findByEntityTypeIdAndAttributeId(Integer entTypeId, Integer attrId);
}

