package com.roomrent.roomrenttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.roomrent.roomrenttracker.entity.User;
import com.roomrent.roomrenttracker.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        return UserRepository.save(user);
    }
}