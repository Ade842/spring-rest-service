package com.example.demo.service;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private List<User> users = Arrays.asList(
            new User("adelisa", "softic", "8327502357", "emailadelisa"),
            new User("a", "s", "357", "a"),
            new User("ad", "s", "832357", "ea")
    );

    public List<User> getAllUsers(){
        return userRepository.findAll();

    }

    public User getUserById(long id) {
        if (userRepository.findById(id).isPresent())
            return userRepository.findById(id).get();
        else
            return null;

    }

    public long saveOrUpdate(User user)
    {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public void delete(long id)
    {
        userRepository.deleteById(id);
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
