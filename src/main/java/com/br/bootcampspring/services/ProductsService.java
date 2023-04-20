package com.br.bootcampspring.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.bootcampspring.domain.Categories;
import com.br.bootcampspring.domain.Products;
import com.br.bootcampspring.dtos.IRequesCategory;
import com.br.bootcampspring.dtos.IRequestProduct;
import com.br.bootcampspring.repositories.CategoriesRepository;
import com.br.bootcampspring.repositories.ProductsRepository;
import com.br.bootcampspring.repositories.exceptions.ResourceNotFoundException;

@Service
public class ProductsService {
  @Autowired
  ProductsRepository productsRepository;

  @Autowired
  CategoriesRepository categoriesRepository;

  public List<IRequestProduct> findAllNoPaginable() {
    List<Products> list = productsRepository.findAll();
    return list.stream().map(p -> new IRequestProduct(p, p.getCategories())).collect(Collectors.toList());
  }

  public Page<Products> findAllNoPaginable(Pageable pageable) {
    return productsRepository.findAll(pageable);
  }

  public IRequestProduct findOne(UUID id) {
    Products productExists = productsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product by id not found"));
    return new IRequestProduct(productExists, productExists.getCategories());
  }

  public void delete(UUID id) {
    Products productsExists = productsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product by id not found"));
    productsRepository.delete(productsExists);
  }

  private void copyDtoToEntity(IRequestProduct dto, Products entity) {

    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setImage_url(dto.getImage_url());
    entity.setDate(dto.getDate());
    entity.setPrice(dto.getPrice());

    entity.getCategories().clear();
    for (IRequesCategory catDto : dto.getCategories()) {
      Categories catExists = categoriesRepository.findById(catDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Category by id not found"));
      entity.getCategories().add(catExists);
    }

  }

  public IRequestProduct create(IRequestProduct data) {
   Products p = new Products();
   copyDtoToEntity(data, p);
   productsRepository.save(p);
   return new IRequestProduct(p);
  }

  public IRequestProduct update(IRequestProduct data) {
    Products p = new Products();
    copyDtoToEntity(data, p);
    productsRepository.save(p);
    return new IRequestProduct(p);
  }

}
