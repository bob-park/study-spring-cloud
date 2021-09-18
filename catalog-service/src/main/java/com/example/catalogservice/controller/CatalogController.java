package com.example.catalogservice.controller;

import com.example.catalogservice.commons.dto.api.response.ResponseCatalog;
import com.example.catalogservice.commons.entity.Catalog;
import com.example.catalogservice.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("catalog-service")
public class CatalogController {

  private final Environment env;

  private final CatalogService catalogService;

  public CatalogController(Environment env, CatalogService catalogService) {
    this.env = env;
    this.catalogService = catalogService;
  }

  @GetMapping(path = "health_check")
  public String status() {
    return String.format(
        "It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
  }

  @GetMapping(path = "catalogs")
  public List<ResponseCatalog> getCatalogs() {
    List<Catalog> catalogs = catalogService.getAllCatalogs();

    return catalogs.stream()
        .map(catalog -> new ModelMapper().map(catalog, ResponseCatalog.class))
        .collect(Collectors.toList());
  }
}
