package com.wjaronski.debter.server.domain;

import lombok.Data;

/**
 * Created by Wojciech Jaronski
 */

@Data
public class UserRequest {
  private String username;
  private String password;
}
