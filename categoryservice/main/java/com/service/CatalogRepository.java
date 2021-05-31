package com.service;

import com.service.jpa.catalogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CatalogRepository extends CrudRepository<catalogEntity,Integer> {
    catalogEntity findByItemId(String userId);
}
