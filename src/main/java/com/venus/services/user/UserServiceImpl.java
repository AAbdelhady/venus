package com.venus.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venus.domain.entities.user.User;
import com.venus.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    protected final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
