package com.example.demo.response;

import java.util.List;

public class GetAllUsersResponse {

  private List<UserResponse> allUsers;

  public GetAllUsersResponse() {
  }

  public GetAllUsersResponse(final List<UserResponse> allUsers) {
    this.allUsers = allUsers;
  }

  public final List<UserResponse> getAllUsers() {
    return allUsers;
  }

  public void setAllUsers(final List<UserResponse> allUsers) {
    this.allUsers = allUsers;
  }

  @Override
  public String toString() {
    String allUsersString = "";
    for (int i = 0; i < allUsers.size(); i++) {
      allUsersString += " " + allUsers.get(i).userResponseString();
    }
    return allUsersString;
  }
}
