package com.venus.feature.user.service;

import org.springframework.stereotype.Service;

import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.venus.config.security.utils.SecurityUtil.getCurrentUserId;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findAuthorizedUser() {
        Long authorizedUserId = getCurrentUserId().orElseThrow(IllegalArgumentException::new);
        return userRepository.findById(authorizedUserId).orElseThrow(IllegalArgumentException::new);
    }
}
