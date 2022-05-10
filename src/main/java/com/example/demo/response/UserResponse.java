package com.example.demo.response;

public class UserResponse {

  private long id;
  private String displayName;
  private String displaySurname;
  private String phoneNumber;
  private String email;


  public UserResponse() {
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public String getDisplaySurname() {
    return displaySurname;
  }

  public void setDisplaySurname(final String displaySurname) {
    this.displaySurname = displaySurname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "{'displayName': " + getDisplayName() + ", " + "'displaySurname': " + getDisplaySurname() + ", " + "'phoneNumber': " + getPhoneNumber() + ", " + "'email': " + getEmail() + "}";
  }

}
