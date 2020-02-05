package com.venus.config.security.utils;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;

import static com.venus.testutils.AssertionUtils.assertUserInSecurityContext;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SecurityUtilTest {

    @Test
    public void getCurrentUserId_shouldReturnUserIdFromSecurityContext_whenUserAuthenticated() {
        // given
        User user = createDummyUser(Role.CUSTOMER);
        setContext(user);

        // when
        Optional<Long> result = SecurityUtil.getCurrentUserId();

        // then
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get());
    }

    @Test
    public void getCurrentUserId_shouldReturnEmptyOptional_whenUserNotAuthenticated() {
        // given
        SecurityContextHolder.clearContext();

        // when
        Optional<Long> result = SecurityUtil.getCurrentUserId();

        // then
        assertFalse(result.isPresent());
    }

    @Test
    public void updateCurrentUserContext_shouldSetContext_whenContextIsClear() {
        // given
        SecurityContextHolder.clearContext();

        User user = createDummyUser(Role.ARTIST);

        // when
        SecurityUtil.updateCurrentUserContext(user);

        // then
        assertUserInSecurityContext(user);
    }

    @Test
    public void updateCurrentUserContext_shouldUpdateContext_whenContextIsNotClear() {
        // given
        User oldUser = createDummyUser(Role.CUSTOMER);
        setContext(oldUser);

        User updatedUser = createDummyUser(Role.ARTIST);

        // when
        SecurityUtil.updateCurrentUserContext(updatedUser);

        // then
        assertUserInSecurityContext(updatedUser);
    }

    private void setContext(User user) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().getAuthority());
        UsernamePasswordAuthenticationToken oldAuth = new UsernamePasswordAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(oldAuth);
    }
}