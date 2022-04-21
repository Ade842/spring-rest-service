package com.example.demo.controller;

import com.example.demo.data.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.SavingUserRequest;
import com.example.demo.response.CreateResponse;
import com.example.demo.response.UserResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping()
  public List<User> getAllUsers() {
     return  userService.getAllUsers();
    }

  @GetMapping("/{id}")
  private UserResponse getUserById(@PathVariable long id) {
    return userService.getUserById(id);
  }

  @DeleteMapping("/{id}")
  private void deleteUser(@PathVariable("id") int id) {
    userService.delete(id);
  }

  @PostMapping()
  private CreateResponse saveUser(@RequestBody CreateUserRequest user) {
    return userService.createUser(user);
  }

  @PutMapping("/{id}")
  public UserResponse update(@PathVariable long id, @RequestBody SavingUserRequest user) {
    userService.updateUser(id, user);
    return userService.getUserById(id);
  }
}
