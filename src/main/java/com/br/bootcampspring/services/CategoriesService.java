package com.br.bootcampspring.services;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.bootcampspring.domain.Categories;
import com.br.bootcampspring.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriesService {
  private final CategoriesRepository categoriesRepository;

  public List<Categories> findAllCategoriesNoPaginable(){
    return categoriesRepository.findAll();
  }
  public Page<Categories> findAll(Pageable pageable){
    return categoriesRepository.findAll(pageable);
  }

  public Categories findId(UUID id){
    return categoriesRepository.findById(id)
    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND ID"+ id));
  }

  @Transactional
  public Categories create(Categories categorie){
    return categoriesRepository.save(categorie);
  }

  @Transactional
  public Categories update(Categories categorie){
    findId(categorie.getId());
    return categoriesRepository.save(categorie);
  }
  public void remove(UUID id){
    Categories categoryExists = findId(id);
    categoriesRepository.delete(categoryExists);
  }
}
