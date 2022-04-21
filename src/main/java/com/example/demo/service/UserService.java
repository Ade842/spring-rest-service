package com.example.demo.service;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.UpdateUserRequest;
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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id) {
            return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
    }

    public long createUser(CreateUserRequest createUserRequest) {
        try {
            return userRepository.save(createUserFromUserRequest(createUserRequest)).getId();
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

    public void updateUser(long id, UpdateUserRequest userDetails) {
        User updateUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
        updateUser.setDisplayName(userDetails.getDisplayName());
        updateUser.setDisplaySurname(userDetails.getDisplaySurname());
        updateUser.setPhoneNumber(userDetails.getPhoneNumber());
        updateUser.setEmail(userDetails.getEmail());
        userRepository.save(updateUser);
    }

}
