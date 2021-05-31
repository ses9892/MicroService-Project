package com.service.controller;

import com.service.jpa.catalogEntity;
import com.service.service.CatalogService;
import com.service.vo.ResponseCatalog;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/catalog-service/")
public class CatalogController {
    Environment env;

    CatalogService catalogService;

    @Autowired
    public CatalogController(Environment env,CatalogService catalogService) {
        this.env = env;
        this.catalogService=catalogService;
    }

    @GetMapping(value = "/health_check/")
    public String healthCheck(HttpServletRequest request){
        return String.format("Good this port(s) is %s",request.getServerPort());
    }

    @GetMapping(value = "/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getUsers(){
        Iterable<catalogEntity> catalogs = catalogService.getAllCatalogs();
        List<ResponseCatalog> users = new ArrayList<>();
        for (catalogEntity catalogEntity : catalogs) {
            users.add(new ModelMapper().map(catalogEntity,ResponseCatalog.class));
        }
        // HATEOAS 해도 되는구간=====
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
