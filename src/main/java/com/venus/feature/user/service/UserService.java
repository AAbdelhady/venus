package com.venus.feature.user.service;

import org.springframework.stereotype.Service;

import com.venus.exceptions.NotFoundException;
import com.venus.exceptions.UnauthorizedException;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.venus.config.security.utils.SecurityUtil.getCurrentUserId;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findAuthorizedUser() {
        Long authorizedUserId = getCurrentUserId().orElseThrow(UnauthorizedException::new);
        return userRepository.findById(authorizedUserId).orElseThrow(NotFoundException::new);
    }

    public User updateAuthorizedUserRole(Role role) {
        User user = findAuthorizedUser();
        user.setRole(role);
        return userRepository.save(user);
    }
}
