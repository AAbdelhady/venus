package com.venus.services.user;

import com.venus.domain.dtos.user.UserResponse;
import com.venus.domain.entities.user.User;

public interface UserService {

    User saveUser(User user);

    UserResponse findAuthorizedUser();
}
