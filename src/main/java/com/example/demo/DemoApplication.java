package com.example.demo;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  public static void main(final String[] args) {

    SpringApplication.run(DemoApplication.class, args);

  }

  @Override
  public void run(final String... args) throws Exception {
    User user = new User();
  }
}
