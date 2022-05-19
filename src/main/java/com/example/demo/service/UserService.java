package com.example.demo.service;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.SavingUserRequest;
import com.example.demo.response.CreateResponse;
import com.example.demo.response.GetAllUsersResponse;
import com.example.demo.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
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
    List<UserResponse> listUserResponse = new ArrayList<UserResponse>() {
    };
    for (User listUser : listUsers) {
      listUserResponse.add(fromUserToUserResponse(listUser));
    }
    return new GetAllUsersResponse(listUserResponse);
  }

  public GetAllUsersResponse getAllUsers() {
    return fromListUserToListUserResponse(userRepository.getNonDeletedUsers());
  }

  public UserResponse getUserById(final long id) {
    try {
      User user = userRepository.getNonDeletedUsersById(id);
      return fromUserToUserResponse(user);

    } catch (Exception e) {
      throw new ResourceNotFoundException("User with id:" + id + " could not be found");
    }
  }

  public CreateResponse createUser(final CreateUserRequest createUserRequest) {
    try {
      //String name = createUserRequest.getDisplayName();
      //String surname = createUserRequest.getDisplaySurname();
      //User user = userRepository.getNonDeletedUsersWithNameAndSurname(name, surname);
      // if (user != null) {
      User savedUser = userRepository.save(createUserFromUserRequest(createUserRequest));
      return fromUserToCreateResponse(savedUser.getId());
      //} else {
      // return fromUserToCreateResponse(0);
      // }
    } catch (Exception e) {
      throw new ApiRequestException("User could not be saved");
    }
  }


  public void delete(final long id) {
    try {
      User user = userRepository.getNonDeletedUsersById(id);
      user.setDeleted(true);
      userRepository.save(user);
    } catch (Exception e) {
      throw new ResourceNotFoundException("User with id:" + id + " could not be found");
    }
  }

  public UserResponse updateUser(final long id, final SavingUserRequest userDetails) {
    try {
      User updateUser = userRepository.getNonDeletedUsersById(id);
      updateUser.setDisplayName(userDetails.getDisplayName());
      updateUser.setDisplaySurname(userDetails.getDisplaySurname());
      updateUser.setPhoneNumber(userDetails.getPhoneNumber());
      updateUser.setEmail(userDetails.getEmail());
      userRepository.save(updateUser);
      return fromUserToUserResponse(updateUser);
    } catch (Exception e) {
      LOGGER.info(e.getMessage());
      throw new ResourceNotFoundException("User with id: " + id + "could not be saved");
    }
  }
}

