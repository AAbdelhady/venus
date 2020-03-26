package com.venus.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import static com.venus.testutils.MapperTestUtils.userMapper;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends MvcTest {
    
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(controller, "userMapper", userMapper);
        init(controller);
    }

    @Test
    public void me() throws Exception {
        // given
        User user = createDummyUser();

        given(userService.findAuthorizedUser()).willReturn(user);

        // when
        mockMvc.perform(get("/user/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.role").value(user.getRole().name()));
    }

}