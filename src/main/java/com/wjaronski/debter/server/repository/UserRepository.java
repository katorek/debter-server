package com.wjaronski.debter.server.repository;

import com.wjaronski.debter.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * Created by Wojciech Jaronski
 */

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
