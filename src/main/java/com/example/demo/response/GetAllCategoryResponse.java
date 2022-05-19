package com.example.demo.response;

import java.util.List;

public class GetAllCategoryResponse {
  private List<CategoryResponse> allCategories;

  public GetAllCategoryResponse() {
  }

  public GetAllCategoryResponse(final List<CategoryResponse> allCategories) {
    this.allCategories = allCategories;
  }

  public List<CategoryResponse> getAllCategories() {
    return allCategories;
  }

  @Override
  public String toString() {
    String allCategoriesString = "";
    for (CategoryResponse allCategory : allCategories) {
      allCategoriesString += " " + allCategories;
    }
    return allCategoriesString;
  }
}
