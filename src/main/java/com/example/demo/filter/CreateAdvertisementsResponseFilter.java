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
public class CreateAdvertisementsResponseFilter implements Filter {
  private static final Logger LOGGER = LoggerFactory.getLogger(CreateAdvertisementsResponseFilter.class);

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    LOGGER.info("In CreateAdvertisementsResponseFilter");
    chain.doFilter(request, response);
  }
}
