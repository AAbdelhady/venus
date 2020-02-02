package com.venus.feature.customer.mapper;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.user.mapper.UserMapper;

@Mapper(config = GlobalMapperConfig.class, uses = UserMapper.class)
public interface CustomerMapper {

    CustomerResponse mapOne(Customer entity);
}
