package com.wjaronski.debter.server.security;

import com.wjaronski.debter.server.domain.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Wojciech Jaronski
 */

@Slf4j
@Component
public class JwtTokenProvider {
  @Value("${app.jwtsecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationInMs}")
  private int jwtExpirationInMs;

  /*public JwtTokenProvider(){
    log.info("jwt {}", jwtSecret);
    log.info("jwt {}", jwtExpirationInMs);
    this.jwtSecret = System.getenv("JWT_SECRET");

    log.info("JWT SECRET: {}", jwtSecret);
  }*/

  @PostConstruct
  public void init() {
    log.info("jwt {}", jwtSecret);
    log.info("jwtExpirationInMs {}", jwtExpirationInMs);
  }

  public String generateToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    return Jwts.builder()
      .setSubject(Long.toString(userPrincipal.getId()))
      .setIssuedAt(new Date())
      .setExpiration(expiryDate)
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();
  }

  public Long getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser()
      .setSigningKey(jwtSecret)
      .parseClaimsJws(token)
      .getBody();
    return Long.parseLong(claims.getSubject());
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }


}
