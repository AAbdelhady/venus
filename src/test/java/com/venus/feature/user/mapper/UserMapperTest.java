package com.venus.feature.user.mapper;

import org.junit.Test;

import com.venus.feature.user.dto.UserResponse;
import com.venus.feature.user.entity.User;

import static com.venus.testutils.AssertionUtils.assertUserEqualsResponse;
import static com.venus.testutils.MapperTestUtils.userMapper;
import static com.venus.testutils.UnitTestUtils.createDummyUser;

public class UserMapperTest {

    @Test
    public void mapOne() {
        // given
        User user = createDummyUser();

        // when
        UserResponse response = userMapper.mapOne(user);

        // then
        assertUserEqualsResponse(user, response);
    }
}