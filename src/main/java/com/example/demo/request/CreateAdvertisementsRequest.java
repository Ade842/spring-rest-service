package com.example.demo.request;


import javax.validation.constraints.NotEmpty;

public class CreateAdvertisementsRequest {

  @NotEmpty(message = "Title is empty")
  private String title;

  @NotEmpty(message = "Description is empty")
  private String description;

  private long userId;

  private long categoryId;

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(final long userId) {
    this.userId = userId;
  }

  public long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(final long categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public String toString() {
    return "{'title': " + getTitle() + ", " + "'descriptioon': " + getDescription() + ", " + "userId:" + getUserId() + ", " + "categoryId:" + getCategoryId() + "}";
  }

}
