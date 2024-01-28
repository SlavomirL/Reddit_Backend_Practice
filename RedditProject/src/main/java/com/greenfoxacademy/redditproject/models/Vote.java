package com.greenfoxacademy.redditproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    private VoteType voteType;

    private int voteStrength;

    public Vote(User user, Post post, VoteType voteType) {
        this.post = post;
        this.user = user;
        this.voteType = voteType;
        this.voteStrength = 1;
    }
}