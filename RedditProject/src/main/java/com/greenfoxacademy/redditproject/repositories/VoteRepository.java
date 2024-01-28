package com.greenfoxacademy.redditproject.repositories;

import com.greenfoxacademy.redditproject.models.Post;
import com.greenfoxacademy.redditproject.models.User;
import com.greenfoxacademy.redditproject.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByUserAndPost(User user, Post post);
}