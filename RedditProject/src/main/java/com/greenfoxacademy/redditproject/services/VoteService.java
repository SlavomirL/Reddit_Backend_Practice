package com.greenfoxacademy.redditproject.services;

import com.greenfoxacademy.redditproject.models.VoteType;
import com.greenfoxacademy.redditproject.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public VoteType checkVoteType(String postAction) {
        if (postAction.equalsIgnoreCase("upvote")) {
            return VoteType.UPVOTE;
        } else if (postAction.equalsIgnoreCase("downvote")) {
            return VoteType.DOWNVOTE;
        } else {
            return null;
        }
    }
}