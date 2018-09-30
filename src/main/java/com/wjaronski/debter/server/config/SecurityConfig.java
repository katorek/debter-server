package com.wjaronski.debter.server.config;

import com.wjaronski.debter.server.service.MyUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Created by Wojciech Jaronski
 */

@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final MyUserDetailsService userDetailsService;
  private final PasswordEncoder encoder;

  public SecurityConfig(MyUserDetailsService userDetailsService, PasswordEncoder encoder) {
    this.userDetailsService = userDetailsService;
    this.encoder = encoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .httpBasic()
      .and()
      .authorizeRequests()
      .antMatchers("/index.html", "/", "/home", "/login", "/register", "/*.js").permitAll()
//        .requestMatchers("*.bundle.*").
      .anyRequest().authenticated()
      .and()
      .csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  private DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(encoder);
    return authProvider;
  }



  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers("*.bundle.*", "*.js");
  }
}
