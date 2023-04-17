package com.br.bootcampspring.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bootcampspring.domain.Categories;
import com.br.bootcampspring.dtos.IRequestCreateCategory;
import com.br.bootcampspring.dtos.IRequestUpdateCategory;
import com.br.bootcampspring.services.CategoriesService;

@RestController
@RequestMapping("categories")

public class CategoriesController {
  private final CategoriesService categoriesService;
  public CategoriesController(CategoriesService categoriesService) {
    this.categoriesService = categoriesService;
  }
  @GetMapping()
  public ResponseEntity<Page<Categories>> findAllPaginable(Pageable pageable){
    return ResponseEntity.ok(categoriesService.findAll(pageable));
  }

  @GetMapping(path = "all")
  public ResponseEntity<List<Categories>> findAll(){
    return ResponseEntity.ok(categoriesService.findAllCategoriesNoPaginable());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Categories> findOne(@PathVariable UUID id){
    Categories findById = categoriesService.findId(id);
    return ResponseEntity.ok(findById);
  }

  @PostMapping
  public ResponseEntity<Categories> create(@RequestBody IRequestCreateCategory category){
    var obj = new Categories();
    BeanUtils.copyProperties(category, obj);
    obj.setName(category.getName());
    return new ResponseEntity<Categories>(categoriesService.create(obj), HttpStatus.CREATED);
  }
  @PutMapping
  public ResponseEntity<Categories> update (@RequestBody IRequestUpdateCategory category){
    var obj = new Categories();
    BeanUtils.copyProperties(category, obj);
    obj.setId(category.getId());
    obj.setName(category.getName());
    return new ResponseEntity<>(categoriesService.update(obj), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remove(@PathVariable UUID id){
    categoriesService.remove(id);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}
