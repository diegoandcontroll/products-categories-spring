package com.br.bootcampspring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bootcampspring.domain.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, UUID>{
  
}
