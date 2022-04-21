package com.example.demo.service;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.response.CreateResponse;
import com.example.demo.response.GetAllUsersResponse;
import com.example.demo.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.SavingUserRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User createUserFromUserRequest(CreateUserRequest createUserRequest){
        User user = new User();
        user.setDisplayName(createUserRequest.getDisplayName());
        user.setDisplaySurname(createUserRequest.getDisplaySurname());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setEmail(createUserRequest.getEmail());
        return user;
    }

    public UserResponse fromUserToUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setDisplayName(user.getDisplayName());
        userResponse.setDisplaySurname(user.getDisplaySurname());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setEmail(user.getEmail());
        userResponse.setId(user.getId());
        return userResponse;
    }

    public CreateResponse fromUserToCreateResponse(long userId){
        CreateResponse userResponse = new CreateResponse();
        userResponse.setId(userId);
        return userResponse;
        }
    public GetAllUsersResponse fromListUserToListUserResponse(List<User> listUsers){
        List<UserResponse> listUserResponse = new ArrayList<>() {} ;
        for (int i = 0; i < listUsers.size(); i++){
            listUserResponse.add(fromUserToUserResponse(listUsers.get(i)));
        }
        GetAllUsersResponse listAllUsersRespone = new GetAllUsersResponse(listUserResponse);
        return listAllUsersRespone;
  }
    public GetAllUsersResponse getAllUsers(){
        return fromListUserToListUserResponse(userRepository.findAll());
    }

    public UserResponse getUserById(long id) {
            return fromUserToUserResponse(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id)));
    }

    public CreateResponse createUser(CreateUserRequest createUserRequest) {
        try {
            User savedUser = userRepository.save(createUserFromUserRequest(createUserRequest));
            return fromUserToCreateResponse(savedUser.getId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not save" );
        }
    }

    public void delete(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not exist with id:" + id);
        }
    }

  public UserResponse updateUser(long id, SavingUserRequest userDetails) {
      User updateUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
      updateUser.setDisplayName(userDetails.getDisplayName());
      updateUser.setDisplaySurname(userDetails.getDisplaySurname());
      updateUser.setPhoneNumber(userDetails.getPhoneNumber());
      updateUser.setEmail(userDetails.getEmail());
      userRepository.save(updateUser);
      return fromUserToUserResponse(updateUser);
  }
}
