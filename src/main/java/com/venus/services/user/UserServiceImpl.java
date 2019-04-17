package com.venus.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venus.domain.entities.user.User;
import com.venus.repositories.UserRepository;

import static com.venus.config.security.utils.SecurityUtil.getCurrentUserId;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findAuthorizedUser() {
        Long authorizedUserId = getCurrentUserId().orElseThrow(IllegalArgumentException::new);
        return userRepository.findById(authorizedUserId).orElseThrow(IllegalArgumentException::new);
    }
}
