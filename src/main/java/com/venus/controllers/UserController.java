package com.venus.controllers;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.user.dto.UserResponse;
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
    public ResponseEntity<UserResponse> me() {
        UserResponse userResponse = userMapper.toDto(userService.findAuthorizedUser());
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(24, TimeUnit.HOURS)).body(userResponse);
    }
}
