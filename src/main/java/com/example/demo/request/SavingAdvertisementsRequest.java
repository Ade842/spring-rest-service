package com.example.demo.request;

import javax.validation.constraints.NotEmpty;

public class SavingAdvertisementsRequest {
  @NotEmpty(message = "Title is empty")
  private String title;

  @NotEmpty(message = "Description is empty")
  private String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }


}
