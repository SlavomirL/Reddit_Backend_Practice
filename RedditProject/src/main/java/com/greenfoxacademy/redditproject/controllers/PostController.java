package com.greenfoxacademy.redditproject.controllers;

import com.greenfoxacademy.redditproject.models.Post;
import com.greenfoxacademy.redditproject.models.User;
import com.greenfoxacademy.redditproject.models.VoteType;
import com.greenfoxacademy.redditproject.services.PostService;
import com.greenfoxacademy.redditproject.services.UserService;
import com.greenfoxacademy.redditproject.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final VoteService voteService;

    @Autowired
    public PostController(PostService postService, UserService userService, VoteService voteService) {
        this.postService = postService;
        this.userService = userService;
        this.voteService = voteService;
    }

    @GetMapping({"/", "/home"})
    public String getHomeByPages(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(name = "username", required = false) String username,
                                 Model model) {
        if (userService.getUserByName(username) == null) {
            return "information-view";
        }
        int pageSize = 5;
        Page<Post> postsPage = postService.getPostsByPage(Math.max(page, 0), pageSize);
        model.addAttribute("posts", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("username", username);
        return "main-page-view";
    }

    @GetMapping("/submit")
    public String getSubmitView(@RequestParam(name = "username") String username, Model model) {
        model.addAttribute("username", username);
        return "add-post";
    }

    @PostMapping("/submit")
    public String submitNewPost(@RequestParam(name = "postTitle") String postTitle,
                                @RequestParam(name = "postURL") String postURL,
                                @RequestParam(name = "username") String username) {
        User user = userService.getUserByName(username);
        postService.addNewPost(postTitle, postURL, user);
        return "redirect:/?username=" + username;
    }

    @GetMapping("/{id}/")
    public String votePost(@PathVariable(name = "id") Long postId,
                           @RequestParam(name = "action") String postAction,
                           @RequestParam(name = "username") String username,
                           @RequestParam(name = "page") int page) {
        User user = userService.getUserByName(username);

        VoteType voteType = voteService.checkVoteType(postAction);

        if (user != null) {
            boolean voteResult = postService.availableForVoting(user, postId, voteType);
            if (voteResult) {
                return "redirect:/?page=" + page + "&username=" + username;
            }
        }
        return "redirect:/?page=" + page + "&username=" + username + "&error=vote_failed";
    }
}