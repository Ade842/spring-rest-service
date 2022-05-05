package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Component
public class SavingAdvertisementsRequestFilter implements Filter {
  private static final Logger LOGGER = LoggerFactory.getLogger(SavingAdvertisementsRequestFilter .class);

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    LOGGER.info("In SavingAdvertisementsRequestFilter ");
    chain.doFilter(request, response);
  }
}
