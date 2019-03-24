package com.venus.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.venus.domain.dtos.customer.CustomerResponse;
import com.venus.domain.entities.user.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    CustomerResponse toDto(Customer entity);
}
