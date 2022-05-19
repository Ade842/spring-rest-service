package com.example.demo.response;

public class CategoryResponse {

  private long id;

  private String categoryName;

  private long userId;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

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

  @Override
  public String toString() {
    return "{categoryName': " + getCategoryName() + ", " + "'userId': " + getUserId() + "}";
  }
}
