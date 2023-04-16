package com.br.bootcampspring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.bootcampspring.domain.Categories;
import com.br.bootcampspring.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriesService {
  private final CategoriesRepository categoriesRepository;
  public List<Categories> findAllCategories(){
    return categoriesRepository.findAll();
  }
}
