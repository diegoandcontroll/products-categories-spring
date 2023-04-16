package com.br.bootcampspring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bootcampspring.domain.Categories;
import com.br.bootcampspring.services.CategoriesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoriesController {
  private final CategoriesService categoriesService;
  @GetMapping
  public ResponseEntity<List<Categories>> findAll(){
    return ResponseEntity.ok(categoriesService.findAllCategories());
  }
}
