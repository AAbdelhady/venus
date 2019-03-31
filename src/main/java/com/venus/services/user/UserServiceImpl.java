package com.venus.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venus.domain.dtos.user.UserResponse;
import com.venus.domain.entities.user.User;
import com.venus.domain.mappers.UserMapper;
import com.venus.repositories.UserRepository;

import static com.venus.config.security.SecurityUtil.getCurrentUserId;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserResponse findAuthorizedUser() {
        Long authorizedUserId = getCurrentUserId().orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(authorizedUserId).orElseThrow(IllegalArgumentException::new);
        return userMapper.toDto(user);
    }
}
