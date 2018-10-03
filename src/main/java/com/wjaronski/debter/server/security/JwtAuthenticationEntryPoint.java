package com.wjaronski.debter.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Wojciech Jaronski
 */

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException e) throws IOException, ServletException {
    log.warn("{}", e.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
      "Przepraszamy, nie jesteś autoryzowany do zarządzania zasobem.");

  }
}
