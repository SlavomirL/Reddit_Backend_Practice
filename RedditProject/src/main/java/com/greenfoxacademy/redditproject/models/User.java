package com.greenfoxacademy.redditproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Post> posts;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}