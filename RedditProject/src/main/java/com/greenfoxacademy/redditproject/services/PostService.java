package com.greenfoxacademy.redditproject.services;

import com.greenfoxacademy.redditproject.models.Post;
import com.greenfoxacademy.redditproject.models.User;
import com.greenfoxacademy.redditproject.models.Vote;
import com.greenfoxacademy.redditproject.models.VoteType;
import com.greenfoxacademy.redditproject.repositories.PostRepository;
import com.greenfoxacademy.redditproject.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public PostService(PostRepository postRepository, VoteRepository voteRepository) {
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public void addNewPost(String postTitle, String postContentLink, User user) {
        postRepository.save(new Post(postTitle, postContentLink, user));
    }

    public boolean availableForVoting(User user, Long postId, VoteType voteType) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return false;
        }
        Post post = postOptional.get();
        Vote checkedVote = voteRepository.findByUserAndPost(user, post);

        if (checkedVote == null) {
            return createNewVote(user, post, voteType);
        } else if (checkedVote.getVoteType() == voteType) {
            return false;
        } else {
            return updateVote(post, checkedVote, voteType);
        }
    }

    private boolean createNewVote(User user, Post post, VoteType voteType) {
        Vote vote = new Vote(user, post, voteType);
        voteRepository.save(vote);

        if (voteType == VoteType.UPVOTE) {
            post.setVotes(post.getVotes() + 1);
        } else if (voteType == VoteType.DOWNVOTE) {
            post.setVotes(post.getVotes() - 1);
        }

        postRepository.save(post);
        return true;
    }

    private boolean updateVote(Post post, Vote checkedVote, VoteType voteType) {
        checkedVote.setVoteType(voteType);
        voteRepository.save(checkedVote);

        if (voteType == VoteType.UPVOTE) {
            post.setVotes(post.getVotes() + 2);
        } else if (voteType == VoteType.DOWNVOTE) {
            post.setVotes(post.getVotes() - 2);
        }

        postRepository.save(post);
        return true;
    }

    public Page<Post> getPostsByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("votes").descending());
        return postRepository.findAll(pageRequest);
    }
}