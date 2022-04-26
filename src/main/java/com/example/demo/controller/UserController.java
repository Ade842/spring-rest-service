package com.example.demo.controller;

import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.SavingUserRequest;
import com.example.demo.response.CreateResponse;
import com.example.demo.response.GetAllUsersResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping()
  public GetAllUsersResponse getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  private UserResponse getUserById(@PathVariable final long id) {
    return userService.getUserById(id);
  }

  @DeleteMapping("/{id}")
  private void deleteUser(@PathVariable("id") final int id) {
    userService.delete(id);
  }

  @PostMapping()
  private CreateResponse saveUser(@RequestBody final CreateUserRequest user) {
    return userService.createUser(user);
  }

  @PutMapping("/{id}")
  public UserResponse update(@PathVariable final long id, @RequestBody final SavingUserRequest user) {
    return userService.updateUser(id, user);
  }
}
