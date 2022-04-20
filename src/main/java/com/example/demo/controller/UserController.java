package com.example.demo.controller;

import com.example.demo.data.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
  private User getBooks(@PathVariable long id) {
     return userService.getUserById(id);
  }

  @DeleteMapping("/{id}")
  private void deleteBook(@PathVariable("id") int id) {
    userService.delete(id);
  }

  @PostMapping()
  private long saveUser(@RequestBody User user) {
    return userService.saveOrUpdate(user);
  }

  @PutMapping("/{id}")
  public User update(@PathVariable long id, @RequestBody User user) {
    userService.updateUser(id, user);
    return user;
  }
}
