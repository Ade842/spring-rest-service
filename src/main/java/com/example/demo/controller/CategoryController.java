package com.example.demo.controller;

import com.example.demo.request.CreateCategoryRequest;
import com.example.demo.request.SavingCategoryRequest;
import com.example.demo.response.CategoryResponse;
import com.example.demo.response.CreateCategoryResponse;
import com.example.demo.response.GetAllCategoryResponse;
import com.example.demo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  private CategoryService categoryService;

  @GetMapping()
  public ResponseEntity<GetAllCategoryResponse> getAllCategories() {
    GetAllCategoryResponse getAllCategoryResponse = categoryService.getAllCategories();
    LOGGER.info("Categories: {}", getAllCategoryResponse);
    return ResponseEntity.ok(getAllCategoryResponse);
  }

  @GetMapping("/{id}")
  private ResponseEntity<CategoryResponse> getCategoryById(@Valid @PathVariable final long id) {
    LOGGER.info("Category with id: " + id + " " + categoryService.getCategoryById(id));
    return ResponseEntity.ok(categoryService.getCategoryById(id));
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<HttpStatus> deleteCategory(@Valid@PathVariable("id") final int id) {
    categoryService.delete(id);
    LOGGER.info("Deleted category with id: " + id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping()
  private ResponseEntity<CreateCategoryResponse> saveCategory(@Valid@RequestBody final CreateCategoryRequest category) {
    LOGGER.info("Created new category: {}", category);
    return ResponseEntity.ok(categoryService.createCategory(category));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> update(@Valid@PathVariable final long id, @Valid@RequestBody final
      SavingCategoryRequest savingCategoryRequest) {
    LOGGER.info("Updated category with id: " + id);
    return ResponseEntity.ok(categoryService.updateCategory(id, savingCategoryRequest));
  }
}
