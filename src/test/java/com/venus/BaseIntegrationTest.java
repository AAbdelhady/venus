package com.venus;

import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.venus.feature.common.enums.AuthProvider;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

@RunWith(SpringRunner.class)
public abstract class BaseIntegrationTest {

    private static final String ALPHABET_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String NUMERALS_STRING = "0123456789";

    @Autowired
    private UserRepository userRepository;

    protected User createUser(Role role) {
        String firstName = randomName();
        User user = User.builder()
                .firstName(firstName)
                .lastName(randomName())
                .authProvider(randomAuthProvider())
                .email(randomEmail(firstName))
                .loginId(randomAlphabeticString(12))
                .role(role)
                .phone(randomNumericString(8))
                .profilePictureUrl(randomPictureUrl())
                .build();
        return userRepository.save(user);
    }

    private AuthProvider randomAuthProvider() {
        AuthProvider[] providers = AuthProvider.values();
        return providers[random(0, providers.length)];
    }

    private String randomName() {
        return randomAlphabeticString(random(6, 16));
    }

    private String randomEmail(String name) {
        String domain = randomAlphabeticString(6);
        return name + "@" + domain + "com";
    }

    private String randomAlphabeticString(int length) {
        return randomString(length, ALPHABET_STRING);
    }

    private String randomNumericString(int length) {
        return randomString(length, NUMERALS_STRING);
    }

    private String randomString(int length, String source) {
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int) (Math.random() * source.length());
            builder.append(source.charAt(character));
        }
        return builder.toString();
    }

    private String randomPictureUrl() {
        return "https://picsum.photos/200/300";
    }

    private static int random(int min, int max) {
        Random r = new Random();
        return r.ints(min, max).findFirst().orElseThrow(RuntimeException::new);
    }
}
