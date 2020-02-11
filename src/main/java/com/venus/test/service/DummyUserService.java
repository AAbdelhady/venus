package com.venus.test.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.venus.feature.common.enums.AuthProvider;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.venus.test.DummyUtils.random;

@Service
@Transactional
@RequiredArgsConstructor
public class DummyUserService {

    private static final Faker faker = new Faker();

    private final UserRepository userRepository;

    public User insertDummyUser(Role role) {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setProfilePictureUrl("https://picsum.photos/200");
        user.setRole(role);
        user.setLoginId(UUID.randomUUID().toString());
        user.setPhone(faker.phoneNumber().phoneNumber());
        user.setEmail(user.getFirstName() + "." + user.getLastName() + "@gmail.com");
        user.setAuthProvider(randomAuthProvider());
        user.setDummy(true);
        return userRepository.save(user);
    }

    private AuthProvider randomAuthProvider() {
        return AuthProvider.values()[random(0, AuthProvider.values().length - 1)];
    }
}
