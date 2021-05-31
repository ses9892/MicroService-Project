package com.service.service;


import com.service.jpa.catalogEntity;
import org.springframework.stereotype.Service;

@Service
public interface CatalogService {
    Iterable<catalogEntity> getAllCatalogs();
}
