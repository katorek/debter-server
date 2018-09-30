package com.wjaronski.debter.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Wojciech Jaronski
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  private String password;
}
