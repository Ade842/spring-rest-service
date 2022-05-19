package com.example.demo.request;

import javax.validation.constraints.NotEmpty;

public class SavingCategoryRequest {
  @NotEmpty(message = "categoryName is empty")
  private String categoryName;

  private long userId;

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(final String categoryName) {
    this.categoryName = categoryName;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(final long userId) {
    this.userId = userId;
  }
}
