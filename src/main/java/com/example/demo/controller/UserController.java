package com.example.demo.controller;

import com.example.demo.data.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.UpdateUserRequest;
import com.example.demo.response.CreateResponse;
import com.example.demo.response.UserResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping()
  public ResponseEntity<List<User>> getAllUsers() {
      return ResponseEntity.ok(userService.getAllUsers());
    }

  @GetMapping("/{id}")
  private ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
     return new ResponseEntity(userService.getUserById(id),HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  private void deleteUser(@PathVariable("id") int id) {
    userService.delete(id);
  }

  @PostMapping()
  private ResponseEntity<CreateResponse> saveUser(@RequestBody CreateUserRequest user) {
    return new ResponseEntity(userService.createUser(user),HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable long id, @RequestBody UpdateUserRequest user) {
    userService.updateUser(id, user);
    return new ResponseEntity(userService.getUserById(id),HttpStatus.OK);
  }
}
