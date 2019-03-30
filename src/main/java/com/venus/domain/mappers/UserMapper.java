package com.venus.domain.mappers;

import org.mapstruct.Mapper;

import com.venus.domain.dtos.user.UserResponse;
import com.venus.domain.entities.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User entity);
}
