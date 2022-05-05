package com.example.demo.controller;

import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.SavingUserRequest;
import com.example.demo.response.CreateResponse;
import com.example.demo.response.GetAllUsersResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  @Autowired
  private UserService userService;

  @GetMapping()
  public ResponseEntity<GetAllUsersResponse> getAllUsers() {
    GetAllUsersResponse getAllUsersResponse = userService.getAllUsers();
    LOGGER .info("Users: {}", getAllUsersResponse);
    return ResponseEntity.ok(getAllUsersResponse);
  }

  @GetMapping("/{id}")
  private ResponseEntity<UserResponse> getUserById(@PathVariable final long id) {
    LOGGER .info("User with id: " + id);
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") final int id) {
    userService.delete(id);
    LOGGER .info("Deleted user with id: " + id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping()
  private ResponseEntity<CreateResponse> saveUser(@RequestBody final CreateUserRequest user) {
    LOGGER.info("Created new user: {}", user);
    return ResponseEntity.ok(userService.createUser(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable final long id, @RequestBody final SavingUserRequest user) {
    LOGGER .info("Updated user with id: " + id);
    return ResponseEntity.ok(userService.updateUser(id, user));
  }
}
