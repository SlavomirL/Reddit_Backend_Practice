package com.greenfoxacademy.redditproject.controllers;

import com.greenfoxacademy.redditproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login-view";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "userPassword") String userPassword,
                        RedirectAttributes redirectAttributes) throws AccessDeniedException {
        redirectAttributes.addAttribute("username", username);
        userService.authenticate(username, userPassword);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register-view";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name = "username") String username,
                           @RequestParam(name = "userPassword") String userPassword,
                           @RequestParam(name = "passwordRepeat") String passwordRepeat) {
        System.out.println("userPassword = " + userPassword);
        System.out.println("passwordRepeat = " + passwordRepeat);
        userService.registerUser(username, userPassword, passwordRepeat);
        return "redirect:/login";
    }
}