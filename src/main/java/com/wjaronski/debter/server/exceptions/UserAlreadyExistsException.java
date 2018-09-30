package com.wjaronski.debter.server.exceptions;

/**
 * Created by Wojciech Jaronski
 */

public class UserAlreadyExistsException extends Exception {
  public UserAlreadyExistsException(String user) {
    super("user already exists: " + user);
  }
}
