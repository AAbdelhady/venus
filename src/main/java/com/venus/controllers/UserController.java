package com.venus.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.user.dto.UserResponse;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.mapper.UserMapper;
import com.venus.feature.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("me")
    public UserResponse me() {
        User user = userService.findAuthorizedUser();
        return userMapper.mapOne(user);
    }
}
