package com.example.demo.response;

public class AdvertisementResponse {

  private long id;
  private String title;
  private String description;
  private long userId;
  private long categoryId;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
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
    return "{'title': " + getTitle() + ", " + "'description': " + getDescription() + "}";
  }

}
