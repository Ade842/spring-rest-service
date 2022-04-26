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

}
