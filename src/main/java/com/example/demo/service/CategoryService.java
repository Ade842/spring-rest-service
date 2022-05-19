package com.example.demo.service;

import com.example.demo.data.entity.Category;
import com.example.demo.data.entity.User;
import com.example.demo.data.repository.CategoryRepository;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.request.CreateCategoryRequest;
import com.example.demo.request.SavingCategoryRequest;
import com.example.demo.response.CategoryResponse;
import com.example.demo.response.CreateCategoryResponse;
import com.example.demo.response.GetAllCategoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private UserRepository userRepository;

  public Category createCategoryFromCategoryRequest(
      final CreateCategoryRequest createCategoryRequest, final User user) {
    Category category = new Category();
    category.setCategoryName(createCategoryRequest.getCategoryName());
    category.setUser(user);
    return category;
  }

  public CategoryResponse fromCategoryToCategoryResponse(final Category category, final User user) {
    CategoryResponse categoryResponse = new CategoryResponse();
    categoryResponse.setId(category.getId());
    categoryResponse.setCategoryName(category.getCategoryName());
    categoryResponse.setUserId(category.getUser().getId());
    return categoryResponse;
  }

  public CreateCategoryResponse fromCategoryToCreateCategoryResponse(final long categoryId) {
    CreateCategoryResponse categoryResponse = new CreateCategoryResponse();
    categoryResponse.setId(categoryId);
    return categoryResponse;
  }

  public GetAllCategoryResponse fromListCategoriesToListCategoriesResponse(
      final List<Category> listCategories) {
    List<CategoryResponse> listCategoryResponse = new ArrayList<>();
    for (Category listCategory : listCategories) {
      listCategoryResponse.add(fromCategoryToCategoryResponse(listCategory, listCategory.getUser()));
    }
    return new GetAllCategoryResponse(listCategoryResponse);
  }

  public GetAllCategoryResponse getAllCategories() {
    return fromListCategoriesToListCategoriesResponse(categoryRepository.getAllCategory());
  }

  public CategoryResponse getCategoryById(final long id) {
    try {
      Category category = categoryRepository.getCategoryById(id);
      User user = userRepository.getById(category.getUser().getId());
      return fromCategoryToCategoryResponse(category, category.getUser());

    } catch (Exception e) {
      throw new ResourceNotFoundException("Category with id:" + id + " could not be found");
    }
  }

  public CreateCategoryResponse createCategory(final CreateCategoryRequest createCategoryRequest) {
    try {
      User user = userRepository.getNonDeletedUsersById(createCategoryRequest.getUserId());
      if (user != null) {
        Category category = createCategoryFromCategoryRequest(createCategoryRequest, user);
        Category category1 = categoryRepository.save(category);
        return fromCategoryToCreateCategoryResponse(category.getId());
      } else {
        throw new ApiRequestException("Category could not be saved, user does not exists");

      }
    } catch (Exception e) {
      throw new ApiRequestException("Category could not be saved, user does not exists");
    }
  }

  public void delete(final long id) {
    try {
      categoryRepository.deleteById(id);
    } catch (Exception e) {
      throw new ResourceNotFoundException("Category with id: " + id + " could not be found");
    }
  }

  public CategoryResponse updateCategory(final long id, final SavingCategoryRequest savingCategoryRequest) {
    try {
      Category updateCategory = categoryRepository.getCategoryById(id);
      updateCategory.setCategoryName(savingCategoryRequest.getCategoryName());
      categoryRepository.save(updateCategory);
      return fromCategoryToCategoryResponse(updateCategory, userRepository.getById(id));
    } catch (Exception e) {
      throw new ResourceNotFoundException("Category with id:" + id + " could not be found");
    }
  }
}
