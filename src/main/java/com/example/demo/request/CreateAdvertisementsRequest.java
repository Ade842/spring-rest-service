package com.example.demo.request;


import javax.validation.constraints.NotEmpty;

public class CreateAdvertisementsRequest {

  @NotEmpty(message = "Title is empty")
  private String title;

  @NotEmpty(message = "Description is empty")
  private String description;

  //@NotEmpty(message = "UserId is empty")
  private long userId;

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

  @Override
  public String toString() {
    return "{'title': " + getTitle() + ", " + "'descriptioon': " + getDescription() + "}";
  }

}
