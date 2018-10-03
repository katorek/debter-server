package com.wjaronski.debter.server.config;

import com.wjaronski.debter.server.security.JwtAuthenticationEntryPoint;
import com.wjaronski.debter.server.security.JwtAuthenticationFilter;
import com.wjaronski.debter.server.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Wojciech Jaronski
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
  securedEnabled = true,
  jsr250Enabled = true,
  prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomUserDetailsService userDetailsService;
  private final PasswordEncoder encoder;

  private final JwtAuthenticationEntryPoint unauthorizedHandler;

  @Autowired
  public SecurityConfig(CustomUserDetailsService userDetailsService, PasswordEncoder encoder, JwtAuthenticationEntryPoint unauthorizedHandler) {
    this.userDetailsService = userDetailsService;
    this.encoder = encoder;
    this.unauthorizedHandler = unauthorizedHandler;
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
//      .httpBasic()
//      .and()
      .cors()
      .and()
      .csrf()
      .disable()
      .exceptionHandling()
      .authenticationEntryPoint(unauthorizedHandler)
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers("/",
        "/favicon.ico",
        "/**/*.png",
        "/**/*.gif",
        "/**/*.svg",
        "/**/*.jpg",
        "/**/*.html",
        "/**/*.css",
        "/**/*.js",
        "/*.js",
        "/*.png",
        "/*.gif",
        "/*.svg",
        "/*.jpg",
        "/*.html",
        "/*.css",
        "/index.html",
        "/home",
        "/login",
        "/register",
        "/signin",
        "/singup"
      )
      .permitAll()
      .antMatchers("/api/auth/**")
      .permitAll()
//        .requestMatchers("*.bundle.*").
//      .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
//        .permitAll()
      .anyRequest()
      .authenticated();

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//      .csrf()
//      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(encoder);
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /*@Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers("*.bundle.*", "*.js");
  }*/
}
