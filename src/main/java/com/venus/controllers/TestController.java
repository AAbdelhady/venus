package com.venus.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.domain.dtos.user.UserResponse;
import com.venus.services.user.UserService;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String test() {
        return new Date().toString();
    }

    @GetMapping("secure")
    public String testSecured() {
        return new Date().toString();
    }

    @GetMapping("me")
    public UserResponse testMe() {
        return userService.findAuthorizedUser();
    }
}
