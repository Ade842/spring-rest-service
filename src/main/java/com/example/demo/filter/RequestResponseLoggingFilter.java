package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestResponseLoggingFilter implements Filter {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(res);

    chain.doFilter(requestWrapper, responseWrapper);

    String requestBody = getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
    String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

    LOGGER.info("Logging Request  {} : {} : {} ", req.getMethod(), req.getRequestURI(), requestBody);

    LOGGER.info("Logging Response {} : {}", res.getContentType(), responseBody);

    responseWrapper.copyBodyToResponse();
  }

  private String getStringValue(final byte[] contentAsByteArray, final String characterEncoding) {
    try {
      return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    return "";
  }
}
