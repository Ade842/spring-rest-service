package com.example.demo.response;

import java.util.List;

public class GetAllUsersResponse {

    private List<UserResponse> allUsers;

    public GetAllUsersResponse() {
    }

    public GetAllUsersResponse(List<UserResponse> allUsers) {
        this.allUsers = allUsers;
    }

    public List<UserResponse> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserResponse> allUsers) {
        this.allUsers = allUsers;
    }
}
