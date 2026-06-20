package com.roomrent.roomrenttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.roomrent.roomrenttracker.entity.User;
import com.roomrent.roomrenttracker.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    // public String registerPage(Model model) {
    public String registerPage() {
        // model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user) {
        System.out.println("User Registration Called");
        userService.registerUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}