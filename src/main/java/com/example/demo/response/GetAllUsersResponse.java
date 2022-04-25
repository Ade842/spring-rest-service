package com.example.demo.response;

import java.util.List;

public class GetAllUsersResponse {

    public GetAllUsersResponse() {}
    private List<UserResponse> allUsers;

    public GetAllUsersResponse(List<UserResponse> allUsers) {
        this.allUsers = allUsers;
    }

    public List<UserResponse> getAllUsers() {return allUsers;}

    public void setAllUsers(List<UserResponse> allUsers) {this.allUsers = allUsers;}

}
