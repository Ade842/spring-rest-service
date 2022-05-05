package com.example.demo;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.filter.ResponseLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(ResponseLoggingFilter.class);

  public static void main(final String[] args) {

    SpringApplication.run(DemoApplication.class, args);

    LOGGER.trace("for tracing purpose");
    LOGGER.debug("for debugging purpose");
    LOGGER.info("for informational purpose");
    LOGGER.warn("for warning purpose");
    LOGGER.error("for logging errors");
  }

  @GetMapping("/index")
  public String index() {
    return "Welcome";
  }

  @Override
  public void run(final String... args) throws Exception {
    User user = new User();
  }
}
