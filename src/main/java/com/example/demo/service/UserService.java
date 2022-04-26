package com.example.demo.service;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.SavingUserRequest;
import com.example.demo.response.CreateResponse;
import com.example.demo.response.GetAllUsersResponse;
import com.example.demo.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User createUserFromUserRequest(final CreateUserRequest createUserRequest) {
    User user = new User();
    user.setDisplayName(createUserRequest.getDisplayName());
    user.setDisplaySurname(createUserRequest.getDisplaySurname());
    user.setPhoneNumber(createUserRequest.getPhoneNumber());
    user.setEmail(createUserRequest.getEmail());
    return user;
  }

  public UserResponse fromUserToUserResponse(final User user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setDisplayName(user.getDisplayName());
    userResponse.setDisplaySurname(user.getDisplaySurname());
    userResponse.setPhoneNumber(user.getPhoneNumber());
    userResponse.setEmail(user.getEmail());
    userResponse.setId(user.getId());
    return userResponse;
  }

  public CreateResponse fromUserToCreateResponse(final long userId) {
    CreateResponse userResponse = new CreateResponse();
    userResponse.setId(userId);
    return userResponse;
  }

  public GetAllUsersResponse fromListUserToListUserResponse(final List<User> listUsers) {
    List<UserResponse> listUserResponse = new ArrayList<>() {
    };
    for (User listUser : listUsers) {
      listUserResponse.add(fromUserToUserResponse(listUser));
    }
    return new GetAllUsersResponse(listUserResponse);
  }

  public GetAllUsersResponse getAllUsers() {
    return fromListUserToListUserResponse(userRepository.findAll());
  }

  public UserResponse getUserById(final long id) {
    return fromUserToUserResponse(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id)));
  }

  public CreateResponse createUser(final CreateUserRequest createUserRequest) {
    try {
      User savedUser = userRepository.save(createUserFromUserRequest(createUserRequest));
      return fromUserToCreateResponse(savedUser.getId());
    } catch (Exception e) {
      throw new ResourceNotFoundException("User not save");
    }
  }

  public void delete(final long id) {
    try {
      userRepository.deleteById(id);
    } catch (Exception e) {
      throw new ResourceNotFoundException("User not exist with id:" + id);
    }
  }

  public UserResponse updateUser(final long id, final SavingUserRequest userDetails) {
    User updateUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
    updateUser.setDisplayName(userDetails.getDisplayName());
    updateUser.setDisplaySurname(userDetails.getDisplaySurname());
    updateUser.setPhoneNumber(userDetails.getPhoneNumber());
    updateUser.setEmail(userDetails.getEmail());
    userRepository.save(updateUser);
    return fromUserToUserResponse(updateUser);
  }
}
