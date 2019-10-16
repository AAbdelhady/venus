package com.venus.feature.user.mapper;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.user.dto.UserResponse;
import com.venus.feature.user.entity.User;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {

    UserResponse toDto(User entity);
}
