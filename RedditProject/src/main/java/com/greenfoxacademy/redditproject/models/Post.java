package com.greenfoxacademy.redditproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contentLink;

    @Column(columnDefinition = "int default 0")
    private int votes;

    @DateTimeFormat(pattern = "dd-MMM-yyyy HH:mm")
    private Date creationDate;

    @ManyToOne
    private User owner;

    public Post(String title, String contentLink, User owner) {
        this.title = title;
        this.contentLink = contentLink;
        this.votes = 0;
        this.creationDate = new Date();
        this.owner = owner;
    }
}