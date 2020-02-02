package com.venus.feature.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.venus.feature.common.enums.Role;

import lombok.Data;

@Data
public class UserResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("role")
    private Role role;
}
