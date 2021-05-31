package com.service.service;

import com.service.CatalogRepository;
import com.service.jpa.catalogEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class CatalogServiceImpl implements CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    @Override
    public Iterable<catalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
