package com.greenfoxacademy.redditproject.repositories;

import com.greenfoxacademy.redditproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}