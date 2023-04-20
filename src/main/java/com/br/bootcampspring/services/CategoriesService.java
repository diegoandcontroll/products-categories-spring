package com.br.bootcampspring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.bootcampspring.domain.Categories;
import com.br.bootcampspring.dtos.IRequesCategory;
import com.br.bootcampspring.repositories.CategoriesRepository;
import com.br.bootcampspring.repositories.exceptions.ResourceNotFoundException;

import lombok.var;

@Service
public class CategoriesService {
  @Autowired
  CategoriesRepository categoriesRepository;

  public List<Categories> findAllCategoriesNoPaginable() {
    return categoriesRepository.findAll();
  }

  public Page<Categories> findAll(Pageable pageable) {
    return categoriesRepository.findAll(pageable);
  }

  public IRequesCategory findId(UUID id) {
    Optional<Categories> catExistsRef = categoriesRepository.findById(id);
    Categories categoriesExist = catExistsRef.orElseThrow(() -> new ResourceNotFoundException("Category by id not found"));
    return new IRequesCategory(categoriesExist);
  }

  @Transactional
  public IRequesCategory create(IRequesCategory category) {
    var obj = new Categories();
    BeanUtils.copyProperties(category, obj);
    categoriesRepository.save(obj);
    IRequesCategory objReturn = new IRequesCategory();
    objReturn.setId(obj.getId());
    objReturn.setName(obj.getName());
    return objReturn;
  }

  @Transactional
  public IRequesCategory update(IRequesCategory category) {
    Categories obj = categoriesRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category by id not found"));
    BeanUtils.copyProperties(category, obj);
    categoriesRepository.save(obj);
    IRequesCategory objReturn = new IRequesCategory();
    objReturn.setId(obj.getId());
    objReturn.setName(obj.getName());
    return objReturn;
  }

  @Transactional
  public void remove(UUID id) {
    Optional<Categories> objExists = categoriesRepository.findById(id);
    categoriesRepository.delete(objExists.get());
  }
}
