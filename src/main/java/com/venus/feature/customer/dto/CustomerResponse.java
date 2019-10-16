package com.venus.feature.customer.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.venus.feature.user.dto.UserResponse;

import lombok.Data;

@Data
public class CustomerResponse {

    @JsonUnwrapped
    private UserResponse user;
}
