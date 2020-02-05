package com.venus.feature.user.repository;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.venus.DbTest;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserRepositoryIntegrationTest extends DbTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findOneByLoginId_shouldReturnCorrectUser() {
        // given
        User user = createUser(Role.UNSPECIFIED);

        // when
        Optional<User> result = userRepository.findOneByLoginId(user.getLoginId());

        // then
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
    }
}