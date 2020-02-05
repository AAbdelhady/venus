package com.venus.feature.user.service;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.context.SecurityContextHolder;

import com.venus.exceptions.NotFoundException;
import com.venus.exceptions.UnauthorizedException;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import static com.venus.config.security.utils.SecurityUtil.updateCurrentUserContext;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new UserService(userRepository);
    }

    @Test
    public void findAuthorizedUser_shouldFindCorrectUser_whenUserAuthenticated() {
        // given
        User expected = createDummyUser();
        updateCurrentUserContext(expected);

        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        // when
        User actual = service.findAuthorizedUser();

        // then
        assertEquals(expected, actual);
    }

    @Test(expected = UnauthorizedException.class)
    public void findAuthorizedUser_shouldThrowUnauthorizedException_whenUserNotAuthenticated() {
        // given
        SecurityContextHolder.clearContext();

        // when
        service.findAuthorizedUser();
    }

    @Test(expected = NotFoundException.class)
    public void findAuthorizedUser_shouldThrowNotFoundException_whenUserNotInDatabase() {
        // given
        User expected = createDummyUser();
        updateCurrentUserContext(expected);

        when(userRepository.findById(expected.getId())).thenReturn(Optional.empty());

        // when
        service.findAuthorizedUser();
    }

    @Test
    public void updateAuthorizedUserRole_shouldUpdateRoleSuccessfully() {
        // given
        User expected = createDummyUser();
        updateCurrentUserContext(expected);

        when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        when(userRepository.save(any(User.class))).then(userSaveAnswer());

        assertEquals(Role.UNSPECIFIED, expected.getRole());

        // when
        User actual = service.updateAuthorizedUserRole(Role.ARTIST);

        // then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(Role.ARTIST, actual.getRole());
    }

    private Answer<User> userSaveAnswer() {
        return invocation -> invocation.getArgument(0);
    }
}