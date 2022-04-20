package com.example.demo.service;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id) {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
            return user;
    }

    public long saveOrUpdate(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public void delete(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not exist with id:" + id);
        }
    }

    public void updateUser(long id, User userDetails) {
        User updateUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
        updateUser.setDisplayName(userDetails.getDisplayName());
        updateUser.setDisplaySurname(userDetails.getDisplaySurname());
        updateUser.setPhoneNumber(userDetails.getPhoneNumber());
        updateUser.setEmail(userDetails.getEmail());
        userRepository.save(updateUser);

    }

}
