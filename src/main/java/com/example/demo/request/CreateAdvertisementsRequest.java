package com.example.demo.request;


public class CreateAdvertisementsRequest {
  private String title;
  private String description;
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
