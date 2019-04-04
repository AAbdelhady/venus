package com.venus.controllers;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.domain.dtos.user.UserResponse;
import com.venus.domain.mappers.UserMapper;
import com.venus.services.user.UserService;

@RestController
@RequestMapping(UserController.URL)
public class UserController {

    static final String URL = "/api/user";

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("me")
    public ResponseEntity<UserResponse> me() {
        UserResponse userResponse = userMapper.toDto(userService.findAuthorizedUser());
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(24, TimeUnit.HOURS)).body(userResponse);
    }
}
