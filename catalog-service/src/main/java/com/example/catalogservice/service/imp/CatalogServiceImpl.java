package com.example.catalogservice.service.imp;

import com.example.catalogservice.commons.entity.Catalog;
import com.example.catalogservice.repository.CatalogRepository;
import com.example.catalogservice.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final CatalogRepository catalogRepository;

  public CatalogServiceImpl(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  @Override
  public List<Catalog> getAllCatalogs() {
    return catalogRepository.findAll();
  }
}
