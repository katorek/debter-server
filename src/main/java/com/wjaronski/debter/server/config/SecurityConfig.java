package com.wjaronski.debter.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Created by Wojciech Jaronski
 */

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
//      .formLogin().
//      .formLogin().loginPage("/login").permitAll()
//      .and()
//      .formLogin()
//      .loginPage("/login")
//    .and()
      .httpBasic()
      .and()
      .authorizeRequests()
      .antMatchers("/index.html", "/", "/home", "/login", "/*.js").permitAll()
//        .requestMatchers("*.bundle.*").
      .anyRequest().authenticated()
      .and()
      .csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers("*.bundle.*", "*.js");
  }
}
